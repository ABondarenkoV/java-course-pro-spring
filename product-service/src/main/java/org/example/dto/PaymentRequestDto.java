package org.example.dto;

import java.math.BigDecimal;

public record PaymentRequestDto(Long userId, Long productId, BigDecimal amount, String operation) {
}
