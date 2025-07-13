package org.example.dto;

import java.math.BigDecimal;

public record BalanceResponseDto(String accountNumber, BigDecimal balance) {
}
