package wallet.topup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import wallet.topup.enums.Status;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseDTO {

    @Schema(name = "id", description = "Transaction UUID", type = "String", required = true)
    private String id;

    @Schema(name = "created", description = "Transaction Time", type = "Date", required = true)
    private LocalDateTime created;

    @Schema(name = "status", description = "Transaction Status", type = "Status", required = true)
    private Status status;

    @Schema(name = "amount", description = "Transaction Amount", type = "double", required = true)
    private double amount;

    @Schema(name = "currency", description = "Transaction Currency", type = "String", required = true)
    private String currency;

    @Schema(name = "charge_id", description = "Charge Id", type = "String", required = true)
    private String charge_id;

    @Schema(name = "customer", description = "Customer Object", type = "CustomerDTO", required = true)
    private CustomerDTO customer;

    @Schema(name = "fees", description = "Fees Object", type = "FeesDTO")
    private FeesDTO fees;

    @Schema(name = "balance", description = "Balance Object", type = "BalanceDTO", required = true)
    private BalanceDTO balance;

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "id='" + id + '\'' +
                ", created=" + created +
                ", status=" + status +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", charge_id='" + charge_id + '\'' +
                ", customer=" + customer +
                ", fees=" + fees +
                ", balance=" + balance +
                '}';
    }
}
