package com.oliver.example.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecuritySchemes({
    @SecurityScheme(name = "httpBasic", type = SecuritySchemeType.HTTP, scheme = "basic")
})
public class OpenApiConfig {
  @Bean
  public OpenAPI springShopOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("SpringShop API")
                .description("Spring boot sample application")
                .version("v0.0.1")
                .license(new License()
                    .name("BSD 2-Clause License")
                    .url("http://springdoc.org")
                )
        )
        .security(List.of(new SecurityRequirement().addList("httpBasic")));
  }
}
