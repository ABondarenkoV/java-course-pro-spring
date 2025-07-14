package org.example.dto;

import java.math.BigDecimal;

public record ExecuteRequestDto(Long userId, Long productId, BigDecimal amount, String operation) {
}
