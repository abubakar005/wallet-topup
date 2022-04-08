package wallet.topup.util;

import java.util.UUID;

public class CustomUtil {

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
