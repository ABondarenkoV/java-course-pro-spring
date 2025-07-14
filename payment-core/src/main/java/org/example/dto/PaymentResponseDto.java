package org.example.dto;

import java.math.BigDecimal;

public record PaymentResponseDto(Long id, Long accountNumber, BigDecimal balance,String productType,String transactionDate, String message) {
}
