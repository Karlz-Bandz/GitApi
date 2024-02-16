package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiRequestConfig {

    @Value("${github.access.token}")
    private String githubToken;

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplateBuilder()
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Authorization", "Bearer " + githubToken)
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .build();
    }
}
