package wallet.topup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeesDTO {

    @NonNull
    @Schema(name = "amount", description = "Fee Amount", type = "double", required = true)
    private double amount;

    @NonNull
    @Schema(name = "currency", description = "Fee Currency", type = "String", required = true)
    private String currency;
}
