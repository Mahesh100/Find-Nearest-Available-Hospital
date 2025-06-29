package com.availit.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AvailIt API")
                        .description("Hospital Bed, ICU, Ventilator & Oxygen Availability Management System API.\n\nFeatures:\n- Multiple locations per hospital\n- ICU beds and ventilators tracking\n- Input validation and user-friendly error messages\n- Pagination and sorting for large hospital lists\n- OpenAPI/Swagger documentation\n")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("AvailIt Team")
                                .email("support@availit.com")
                                .url("https://availit.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development server"),
                        new Server()
                                .url("https://api.availit.com")
                                .description("Production server")
                ));
    }
} 