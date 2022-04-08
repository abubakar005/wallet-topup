package wallet.topup.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    /** All currency related validation or transformations will be performed here,
     * so due to limited scope I am assuming only USD currency is being supported in this project */

    // Checking the supported currencies defined in the list. Suppose here we are only supporting USD
    public boolean isValidCurrency(List<String> supportedCurrencies, String currency) {
        if (supportedCurrencies.stream().anyMatch(c -> c.equals(currency)))
            return true;
        else
            return false;
    }
}
