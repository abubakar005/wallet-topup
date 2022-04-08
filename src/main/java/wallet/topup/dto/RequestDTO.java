package wallet.topup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

    @NonNull
    @Schema(name = "amount", description = "Transaction Amount", type = "double", required = true)
    private double amount;

    @NonNull
    @Schema(name = "currency", description = "Transaction Currency", type = "String", required = true)
    private String currency;

    @NonNull
    @Schema(name = "charge_id", description = "Charge Id", type = "String", required = true)
    private String charge_id;

    @NonNull
    @Schema(name = "customer", description = "Customer Object", type = "CustomerDTO", required = true)
    private CustomerDTO customer;

    @Nullable
    @Schema(name = "fees", description = "Fees Object", type = "FeesDTO")
    private FeesDTO fees;

    @Override
    public String toString() {
        return "RequestDTO{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                ", charge_id='" + charge_id + '\'' +
                ", customer=" + customer +
                ", fees=" + fees +
                '}';
    }
}
