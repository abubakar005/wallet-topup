package wallet.topup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.topup.dto.RequestDTO;
import wallet.topup.dto.ResponseDTO;
import wallet.topup.entity.TransactionRequestsEntity;
import wallet.topup.enums.Error;
import wallet.topup.enums.Status;
import wallet.topup.exception.NotFoundException;
import wallet.topup.exception.TopUpException;
import wallet.topup.service.ITopUpService;
import wallet.topup.service.Transaction;

@Service
public class TopUpServiceImpl implements ITopUpService {

    @Autowired
    private Transaction transaction;

    @Override
    public ResponseDTO doTransaction(RequestDTO requestDTO) {

        ResponseDTO responseDTO = null;
        TransactionRequestsEntity transactionRequest = null;

        try {

            // Inserting request data into the table
            transactionRequest = transaction.insertTransactionRequestsData(requestDTO.toString());

            // Request Validations
            transaction.validateRequest(requestDTO);

            // Customer Validations
            transaction.validateCustomer(requestDTO.getCustomer());

            // Fee Validation if exists
            if(requestDTO.getFees() != null)
                transaction.validateFee(requestDTO.getFees());

            // Performing Transaction
            String uuid = transaction.performTransaction(requestDTO);

            responseDTO = transaction.prepareTransactionResponse(requestDTO, uuid);
            responseDTO.setStatus(Status.SUCCESS);

            // Updating success status of request
            transaction.updateTransactionRequestsData(transactionRequest, responseDTO.toString(), Error.SUCCESS_CODE.getCode(), Error.SUCCESS_CODE.getMsg());

        } catch (Exception e) {

            if (e instanceof NotFoundException) {
                NotFoundException nf = (NotFoundException) e;
                transaction.updateTransactionRequestsData(transactionRequest, null, nf.getCode(), nf.getMsg());
                throw nf;
            } else if (e instanceof TopUpException) {
                TopUpException te = (TopUpException) e;
                transaction.updateTransactionRequestsData(transactionRequest, null, te.getCode(), te.getMsg());
                throw te;
            }
            // Updating status of request
            transaction.updateTransactionRequestsData(transactionRequest, null, Error.GENERAL_ERROR.getCode(), Error.GENERAL_ERROR.getMsg());
        }

        return responseDTO;
    }
}
