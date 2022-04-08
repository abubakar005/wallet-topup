package wallet.topup.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.topup.dto.*;
import wallet.topup.entity.*;
import wallet.topup.enums.Error;
import wallet.topup.enums.Status;
import wallet.topup.exception.TopUpException;
import wallet.topup.repository.CustomerRepository;
import wallet.topup.repository.TransactionRepository;
import wallet.topup.repository.TransactionRequestsRepository;
import wallet.topup.repository.WalletRepository;
import wallet.topup.service.Transaction;
import wallet.topup.util.Constant;
import wallet.topup.util.CustomUtil;

import java.time.LocalDateTime;

@Slf4j
@Service
public class TopUpTransaction implements Transaction<RequestDTO> {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private FeeService feeService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionRequestsRepository transactionRequestsRepository;

    @Override
    public TransactionRequestsEntity insertTransactionRequestsData(String request) {
        TransactionRequestsEntity transactionRequest = new TransactionRequestsEntity();
        transactionRequest.setRequestData(request);
        transactionRequest.setCreatedBy("System");
        transactionRequest.setCreationDate(LocalDateTime.now());
        transactionRequestsRepository.save(transactionRequest);

        return transactionRequest;
    }

    @Override
    public void updateTransactionRequestsData(TransactionRequestsEntity transactionRequest, String response, int errorCode, String errorMsg) {
        transactionRequest.setResponseData(response);
        transactionRequest.setErrorCode(errorCode);
        transactionRequest.setErrorMsg(errorMsg);
        transactionRequest.setUpdatedBy("System");
        transactionRequest.setUpdatedDate(LocalDateTime.now());
        transactionRequestsRepository.save(transactionRequest);
    }

    @Override
    public void validateRequest(RequestDTO requestDTO) {

        double amount = requestDTO.getAmount();

        // Checking amount range, defined in the constant file
        if(amount < Constant.AMOUNT_MIN_VALUE)
            throw new TopUpException(Error.MIN_AMOUNT.getCode(), String.format(Error.MIN_AMOUNT.getMsg(), Constant.AMOUNT_MIN_VALUE));

        if(amount > Constant.AMOUNT_MAX_VALUE)
            throw new TopUpException(Error.MAX_AMOUNT.getCode(), String.format(Error.MAX_AMOUNT.getMsg(), Constant.AMOUNT_MAX_VALUE));

        // Assuming that, this is valid charge Id and unique
        String chargeId = requestDTO.getCharge_id();

        // Checking if this charge Id is already successfully processed or not
        if(transactionRepository.findByChargeIdAndStatus(chargeId, Status.SUCCESS.toString()) != null)
            throw new TopUpException(Error.CHARGE_ID_PROCESSED.getCode(), String.format(Error.CHARGE_ID_PROCESSED.getMsg(), chargeId));

        // Currency validation
        String currency = requestDTO.getCurrency();

        if(currency == null)
            throw new TopUpException(Error.CURRENCY_MISSING.getCode(), Error.CURRENCY_MISSING.getMsg());

        if(!currencyService.isValidCurrency(Constant.CURRENCIES, currency))
            throw new TopUpException(Error.CURRENCY_NOT_SUPPORTED.getCode(), String.format(Error.CURRENCY_NOT_SUPPORTED.getMsg(), currency));
    }

    @Override
    public void validateCustomer(CustomerDTO customer) {
        customerService.validateCustomer(customer);
    }

    @Override
    public void validateFee(FeesDTO t) {
        feeService.validateFees(t);
    }

    @Override
    public String performTransaction(RequestDTO requestDTO) {

        FeeEntity feeEntity = null;
        double feeAmount = 0;
        TransactionEntity txn = null;
        String callerId = requestDTO.getCustomer().getId();
        String response = null;

        try {

            double amount = requestDTO.getAmount();
            // Pre-defined GL for wallet transactions like GL to SVA
            CustomerEntity fromAccount = customerRepository.getById(Constant.AMOUNT_GL_ID);

            // Actual payee (Top-up customer)
            CustomerEntity toAccount = customerRepository.getById(callerId);

            // If there is a fee for transaction
            FeesDTO fee = requestDTO.getFees();
            if(fee != null) {
                feeEntity = feeService.saveFee(fee, callerId);
                feeAmount = fee.getAmount();
            }

            // Preparing transaction
            txn = prepareTransaction(fromAccount, toAccount, amount, feeEntity, requestDTO.getCharge_id(), requestDTO.getCurrency(), Constant.GL_TO_WALLET);
            createTransactionStatus(txn, Status.INITIATED.toString(), callerId);

            // Debit balance (GL account)
            debitTransaction(fromAccount, amount, callerId);

            // Credit balance to payee (by deducting fee if exists)
            creditTransaction(toAccount, amount-feeAmount, callerId);

            // Credit to Fee GL, if there is fee
            if(fee != null) {
                // Fee GL payee (Top-up customer)
                toAccount = customerRepository.getById(Constant.FEE_GL_ID);

                creditTransaction(toAccount, feeAmount, callerId);
            }

            // Update transaction
            updateTransactionStatus(txn, Status.SUCCESS.toString(), callerId);
            response = txn.getId();
        } catch (Exception e) {
            log.error("Error: {} ",e.getMessage());
            // Update transaction
            updateTransactionStatus(txn, Status.SUCCESS.toString(), callerId);
        }

        return response;
    }

    private TransactionEntity prepareTransaction(CustomerEntity fromAccount, CustomerEntity toAccount, Double amount, FeeEntity fee,
                                                 String chargeId, String currency, String txnDetail) {

        return new TransactionEntity().toBuilder()
                .id(CustomUtil.getUUID())
                .amount(amount)
                .payer(fromAccount)
                .payee(toAccount)
                .fee(fee)
                .currency(currency)
                .chargeId(chargeId)
                .txnDetail(txnDetail)
                .build();
    }

    private WalletEntity creditTransaction(CustomerEntity payeeAccount, Double amount, String callerId) {
        WalletEntity walletEntity = walletRepository.findByCustomer(payeeAccount);
        walletEntity.setCreditBalance(walletEntity.getCreditBalance()+amount);
        walletEntity.setUpdatedBy(callerId);
        walletEntity.setUpdatedDate(LocalDateTime.now());
        walletRepository.save(walletEntity);

        return walletEntity;
    }

    private WalletEntity debitTransaction(CustomerEntity payerAccount, Double amount, String callerId) {
        WalletEntity walletEntity = walletRepository.findByCustomer(payerAccount);
        walletEntity.setDebitBalance(walletEntity.getDebitBalance()+amount);
        walletEntity.setUpdatedBy(callerId);
        walletEntity.setUpdatedDate(LocalDateTime.now());
        walletRepository.save(walletEntity);

        return walletEntity;
    }

    public void createTransactionStatus(TransactionEntity txn, String status, String callerId) {
        txn.setStatus(status);
        txn.setCreatedBy(callerId);
        txn.setCreationDate(LocalDateTime.now());
        transactionRepository.save(txn);
    }

    public void updateTransactionStatus(TransactionEntity txn, String status, String callerId) {
        txn.setStatus(status);
        txn.setUpdatedBy(callerId);
        txn.setUpdatedDate(LocalDateTime.now());
        transactionRepository.save(txn);
    }

    @Override
    public ResponseDTO prepareTransactionResponse(RequestDTO requestDTO, String uuid) {

        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setId(uuid);
        responseDTO.setAmount(requestDTO.getAmount());
        responseDTO.setCharge_id(requestDTO.getCharge_id());
        responseDTO.setCreated(LocalDateTime.now());
        responseDTO.setCurrency(requestDTO.getCurrency());
        responseDTO.setCustomer(requestDTO.getCustomer());
        responseDTO.setBalance(getBalance(requestDTO.getCustomer().getWallet_id()));

        if(requestDTO.getFees() != null)
            responseDTO.setFees(requestDTO.getFees());
        else
            responseDTO.setFees(getZeroFee());

        return responseDTO;
    }

    private BalanceDTO getBalance(String walletId) {

        BalanceDTO balance = new BalanceDTO();
        WalletEntity walletEntity = walletRepository.findById(walletId).get();
        balance.setTotal_amount(walletEntity.getCreditBalance()-walletEntity.getDebitBalance());

        return balance;
    }

    private FeesDTO getZeroFee() {
        FeesDTO fee = new FeesDTO();
        fee.setAmount(0.00);
        return fee;
    }
}
