package wallet.topup.service;

import wallet.topup.dto.RequestDTO;
import wallet.topup.dto.ResponseDTO;

public interface ITopUpService {

    ResponseDTO doTransaction(RequestDTO requestDTO);
}
