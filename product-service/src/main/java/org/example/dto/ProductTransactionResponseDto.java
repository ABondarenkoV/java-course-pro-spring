package org.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductTransactionResponseDto(Long id,
                                            Long accountNumber,
                                            BigDecimal balance,
                                            String productType,
                                            LocalDateTime transactionDate,
                                            String message) {
}
