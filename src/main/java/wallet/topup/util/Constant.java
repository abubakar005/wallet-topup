package wallet.topup.util;

import java.util.Arrays;
import java.util.List;

public final class Constant {

    private Constant() {
    }

    public static double AMOUNT_MIN_VALUE = 100.00;
    public static double AMOUNT_MAX_VALUE = 1000.00;
    public static Character STATUS_ACTIVE = 'Y';
    public static String GL_TO_WALLET = "Wallet TopUp";

    public static String AMOUNT_GL_ID = "11111111";
    public static String FEE_GL_ID = "22222222";

    public static String CURRENCY_USD = "USD";
    public static List<String> CURRENCIES = Arrays.asList(CURRENCY_USD);

}
