package wallet.topup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.topup.dto.FeesDTO;
import wallet.topup.entity.FeeEntity;
import wallet.topup.enums.Error;
import wallet.topup.exception.TopUpException;
import wallet.topup.repository.FeeRepository;
import wallet.topup.util.Constant;

import java.time.LocalDateTime;

@Service
public class FeeService {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private FeeRepository feeRepository;

    public void validateFees(FeesDTO feesDTO) {

        // Amount can not be null, as this this primitive type

        if(!currencyService.isValidCurrency(Constant.CURRENCIES, feesDTO.getCurrency()))
            throw new TopUpException(Error.CURRENCY_NOT_SUPPORTED.getCode(), String.format(Error.CURRENCY_NOT_SUPPORTED.getMsg(), feesDTO.getCurrency()));
    }

    public FeeEntity saveFee(FeesDTO fee, String callerId) {
        FeeEntity feeEntity = new FeeEntity();
        feeEntity.setCurrency(fee.getCurrency());
        feeEntity.setAmount(fee.getAmount());
        feeEntity.setCreatedBy(callerId);
        feeEntity.setCreationDate(LocalDateTime.now());
        feeEntity = feeRepository.save(feeEntity);
        return feeEntity;
    }
}
