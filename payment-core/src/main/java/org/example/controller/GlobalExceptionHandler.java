package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResponseErrorDto;
import org.example.exception.EntityNotFoundException;
import org.example.exception.InsufficientFundsException;
import org.example.exception.IntegrationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //Обработка ошибок локально

    @ExceptionHandler(IntegrationException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ResponseErrorDto handleIntegrationException(IntegrationException ex) {
        log.warn("Ошибка при интеграции с внешним сервисом: {}", ex.getMessage());
        return new ResponseErrorDto(HttpStatus.BAD_GATEWAY.value() ,ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseErrorDto handleUnexpectedExceptions(Exception ex) {
        log.error("Непредвиденная ошибка в платежном сервисе", ex);
        return new ResponseErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Внутренняя ошибка платежного сервиса");
    }

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

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseErrorDto handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Неверный аргумент: {}", ex.getMessage());
        return new ResponseErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
