package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dto.ResponseErrorDto;
import org.example.exception.InsufficientFundsException;
import org.example.exception.IntegrationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    private final ObjectMapper objectMapper;
    //Обработка ошибок из внешнего сервиса
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatusCode statusCode = response.getStatusCode();
        return statusCode.is4xxClientError() || statusCode.is5xxServerError();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        ResponseErrorDto responseErrorDto = objectMapper.readValue(response.getBody(), ResponseErrorDto.class);
        if (response.getStatusCode() == HttpStatus.PAYMENT_REQUIRED) {
            throw new InsufficientFundsException(responseErrorDto.message());
        } else if (response.getStatusCode().is4xxClientError()) {
            throw new IntegrationException(
                    responseErrorDto.code(),
                    "Ошибка клиента: неверные данные или параметры запроса",
                    responseErrorDto.message()
            );
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new IntegrationException(
                    responseErrorDto.code(),
                    "Произошла ошибка при вызове внешнего сервиса...",
                    responseErrorDto.message()
            );
        }
    }
}
