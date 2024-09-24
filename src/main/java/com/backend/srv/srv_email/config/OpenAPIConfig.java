package com.backend.srv.srv_email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        Info info = new Info()
                .title("Email Service")
                .version("1.0")
                .description("API para serviço de envio de email.");

        return new OpenAPI().info(info);
    }
}