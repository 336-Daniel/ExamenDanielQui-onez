package com.uti.svcorders.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${menu.service.url}")
    private String munuService;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(munuService)
                .defaultHeader("Content-Type","application/json")
                .defaultHeader("Accept","application/json")
                .build();
    }
}
