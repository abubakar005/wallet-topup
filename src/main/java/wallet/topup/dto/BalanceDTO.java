package wallet.topup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public class BalanceDTO {

    @NonNull
    @Schema(name = "total_amount", description = "Total BalanceDTO Amount", type = "double", required = true)
    private double total_amount;
}
