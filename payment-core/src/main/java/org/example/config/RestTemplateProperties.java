package org.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
public class RestTemplateProperties {
    // Свойства одного клиента
    private String url;
    private Duration connectTimeout;
    private Duration readTimeout;
}

