package wallet.topup.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wallet.topup.dto.*;
import wallet.topup.service.ITopUpService;

@Tag(description = "TopUp Wallet Account Resource", name = "TopUp Controller")
@RestController
@RequestMapping("/api/v1")
public class TopUpController {

    @Autowired
    private ITopUpService topUpService;

    @Operation(summary = "TopUp Wallet Account API",
            description = "This API will TopUp the customer account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "${api.response-codes.ok.desc}"),
            @ApiResponse(responseCode = "400",
                    description = "${api.response-codes.badRequest.desc}",
                    content = { @Content(examples = { @ExampleObject(value = "400 - Bad Request") }) }),
            @ApiResponse(responseCode = "404",
                    description = "${api.response-codes.notFound.desc}",
                    content = { @Content(examples = { @ExampleObject(value = "404 - Not Found") }) }) })
    @PostMapping("/top-up")
    public ResponseEntity<ResponseDTO> topUp(@RequestBody RequestDTO requestDTO) {
        return ResponseEntity.ok(topUpService.doTransaction(requestDTO));
    }
}
