package org.example.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Configuration;


@Getter
@ConfigurationProperties(prefix = "integration.clients")
public class RestTemplateClientProperties {
    // Map с настройками всех клиентов
    private final RestTemplateProperties productClient;

    @ConstructorBinding
    public RestTemplateClientProperties(RestTemplateProperties productClient) {
        this.productClient = productClient;
    }
}