package wallet.topup.service;

import wallet.topup.dto.CustomerDTO;
import wallet.topup.dto.FeesDTO;
import wallet.topup.dto.RequestDTO;
import wallet.topup.dto.ResponseDTO;
import wallet.topup.entity.TransactionRequestsEntity;

import java.io.Serializable;

public interface Transaction<T> extends Serializable {

    void validateRequest(T t);
    void validateCustomer(CustomerDTO customer);
    void validateFee(FeesDTO t);
    String performTransaction(RequestDTO requestDTO);
    ResponseDTO prepareTransactionResponse(RequestDTO requestDTO, String uuid);
    void updateTransactionRequestsData(TransactionRequestsEntity transactionRequest, String response, int errorCode, String errorMsg);
    TransactionRequestsEntity insertTransactionRequestsData(String request);
}
