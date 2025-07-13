package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aspect.annotation.LogHttpRequest;
import org.example.dto.ResponseErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestErrorController {
    @LogHttpRequest
    @GetMapping("/error400")
    public ResponseEntity<ResponseErrorDto> error400() {
        log.error("Некорректный запрос: 400");
        return ResponseEntity.status(400).body(new ResponseErrorDto(400, "Некорректный запрос"));
    }
    @LogHttpRequest
    @GetMapping("/error500")
    public ResponseEntity<ResponseErrorDto> error500() {
        log.error("Внутренняя ошибка сервера: 500");
        return ResponseEntity.status(500).body(new ResponseErrorDto(500, "Внутренняя ошибка сервера"));
    }
}
