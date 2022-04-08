package wallet.topup.enums;

public enum Error {

    SUCCESS_CODE(1000, "Transaction done successfully!"),
    GENERAL_ERROR(1001, "General exception occurred while doing transaction"),
    WALLET_NOT_FOUND(1002, "Customer wallet Id not found"),
    INVALID_REQUEST(1003, "Invalid request data"),
    CUSTOMER_NOT_EXISTS(1004, "No customer found against this Id %s"),
    CUSTOMER_NOT_FOUND(1005, "Customer not found"),
    CHARGE_ID_PROCESSED(1006, "Transaction already processed against this charge Id %s"),
    MIN_AMOUNT(1007, "Amount can not be less than %s"),
    MAX_AMOUNT(1008, "Amount can not be grater that %s"),
    INVALID_WALLET_ID(1009, "Customer wallet Id is invalid or inactive"),
    CUSTOMER_NOT_ACTIVE(1010, "Customer is inactive"),
    CURRENCY_NOT_SUPPORTED(1011, "Provided currency %s is invalid or not supported"),
    CURRENCY_MISSING(1012, "currency is missing"),
    ;

    private final int code;
    private final String msg;

    Error(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
