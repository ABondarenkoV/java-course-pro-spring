package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResponseErrorDto;
import org.example.exception.EntityNotFoundException;
import org.example.exception.InsufficientFundsException;
import org.example.exception.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseErrorDto handleEntityNotFound(EntityNotFoundException ex) {
        log.warn("Не найдено: {}", ex.getMessage());
        return new ResponseErrorDto(HttpStatus.NOT_FOUND.value(),ex.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ResponseErrorDto handleInsufficientFunds(InsufficientFundsException ex) {
        log.warn("Недостаточно средств: {}", ex.getMessage());
        return new ResponseErrorDto(HttpStatus.PAYMENT_REQUIRED.value(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseErrorDto handleOtherExceptions(Exception ex) {
        log.error("Уппссс, неожиданная ошибка...", ex);
        return new ResponseErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
    }
    @ExceptionHandler(InvalidOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseErrorDto handleIllegalArgument(InvalidOperationException ex) {
        log.warn("Неверный аргумент: {}", ex.getMessage());
        return new ResponseErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

}
