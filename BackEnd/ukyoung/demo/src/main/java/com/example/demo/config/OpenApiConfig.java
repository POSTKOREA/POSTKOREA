package com.example.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("DMobile API Reference for Developers")
                .description("DMobile 데모 프로젝트의 API 명세서 입니다.")
                .version("1.0");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
