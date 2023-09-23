package com.cristian.callmessageprocessor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI usersMicroservicOpenAPI() {
        return new OpenAPI()
        .info(new Info().title("Call Message Processor")
                         .description("API to retrieve logs of messages and their metrics")
                         .version("1.0"));
    }
}
