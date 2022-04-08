package wallet.topup.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiDocsConfiguration {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wallet Account TopUp Docs")
                        .description("Rest API for Topping Up Wallet")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Abu Bakar")
                                .url("https://github.com/abubakar005/wallet-topup")
                                .email("abubakar.cs@gmail.com"))
                        .termsOfService("TOC")
                        .license(new License().name("License").url("#"))
                );
    }

    @Bean
    public GroupedOpenApi groupOpenApi() {
        String paths[] = {"/api/v1/**"};
        String packagesToScan[] = {"wallet.topup.resource"};
        return GroupedOpenApi.builder()
                .group("topup-wallet-account")
                .pathsToMatch(paths)
                .packagesToScan(packagesToScan)
                .build();
    }
}