package org.example.dto;

import java.math.BigDecimal;

public record BalanceResponseDto(Long accountNumber, BigDecimal balance) {
}
