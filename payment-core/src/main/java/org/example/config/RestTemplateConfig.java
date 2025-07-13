package org.example.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(RestTemplateClientProperties.class)
public class RestTemplateConfig {
    // Конфигурация для создания бинов RestTemplate
    @Bean
    public RestTemplate restTemplate(RestTemplateClientProperties restTemplateClientProperties,
                                     RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        RestTemplateProperties productsClientProp = restTemplateClientProperties.getProductClient();
        return new RestTemplateBuilder()
                .rootUri(productsClientProp.getUrl())
                .connectTimeout(productsClientProp.getConnectTimeout())
                .errorHandler(restTemplateResponseErrorHandler)
                .readTimeout(productsClientProp.getReadTimeout()).build();
    }
}
