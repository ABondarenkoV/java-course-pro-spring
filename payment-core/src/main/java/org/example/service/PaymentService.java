package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ExecuteRequestDto;
import org.example.dto.PaymentResponseDto;
import org.example.dto.ProductResponseDto;
import org.example.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final IntegrationService integrationService;

    public PaymentResponseDto executePayment(ExecuteRequestDto request) {

        if (request.productId() == null || request.userId() == null) {
            throw new EntityNotFoundException("Не указна продукт/пользователь");
        }
        return integrationService.executeTransaction(request);
    }

    public List<ProductResponseDto> getProductsByUser(long userId) {
        List<ProductResponseDto> products = integrationService.getProductsByUserId(userId);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("Продукты для пользователя с ID : " + userId + " не найдены");
        }
        return products;
    }
}
