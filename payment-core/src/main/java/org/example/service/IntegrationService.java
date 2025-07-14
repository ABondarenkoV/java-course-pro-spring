package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.*;
import org.example.exception.IntegrationException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegrationService {
    private final RestTemplate restTemplate ;

    public PingResponseDTO pingProductService() {
        log.info("Ping ... продуктового сервиса");
        PingResponseDTO pingResponseDTO = restTemplate.getForObject("/ping", PingResponseDTO.class);

        if (pingResponseDTO == null){
            throw new IntegrationException(pingResponseDTO.code(),"Произошла ошибка при вызове внешнего сервиса...",pingResponseDTO.message());
        }

        return new PingResponseDTO(pingResponseDTO.code(),pingResponseDTO.message());
    }

    public List<ProductResponseDto> getProductsByUserId(Long userId) {
        log.info("Интеграция: Получение продуктов пользователя по ID : {}", userId);
        ResponseEntity<List<ProductResponseDto>> responseEntity = restTemplate.exchange(
                "/user/{userId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                userId
        );
        List<ProductResponseDto> products = responseEntity.getBody();

        if (products == null || products.isEmpty()) {
            return List.of();
        }
        return products;
    }

    public PaymentResponseDto executeTransaction(ExecuteRequestDto request) {
        log.info("Интеграция: Выполнение платежной транзакции... " + request.operation() );

        PaymentResponseDto response = restTemplate.postForObject(
                "/payment/execute",
                request,
                PaymentResponseDto.class
        );
        System.out.println(response);
        return response;
    }

}
