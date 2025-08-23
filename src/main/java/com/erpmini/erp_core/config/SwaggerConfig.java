package com.erpmini.erp_core.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI erpOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("ERP Mini API")
                        .description("ERP Mini - Auth Module (User, Role, Permission, JWT, AuditLog)")
                        .version("v1.0.0")
                        .license(new License().name("MIT License")));
    }
}