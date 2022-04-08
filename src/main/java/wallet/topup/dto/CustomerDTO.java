package wallet.topup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @NonNull
    @Schema(name = "id", description = "Customer Id", type = "String", required = true)
    private String id;

    @NonNull
    @Schema(name = "wallet_id", description = "Customer Wallet Id", type = "String", required = true)
    private String wallet_id;
}
