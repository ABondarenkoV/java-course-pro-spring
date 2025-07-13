package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aspect.annotation.LogHttpRequest;
import org.example.dto.*;
import org.example.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @LogHttpRequest
    @GetMapping ("/")
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @LogHttpRequest
    @GetMapping ("/user/{id}")
    public List<ProductResponseDto> getProductsByUserId(@PathVariable long id) {
        return productService.getProductsByUserId(id);
    }

    @LogHttpRequest
    @GetMapping ("/{id}")
    public ProductResponseDto getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @LogHttpRequest
    @GetMapping ("/users/{id}/balance")
    public BalanceResponseDto getBalanceByUserId(@PathVariable long id) {
        return productService.getBalanceByUserId(id);
    }
    @LogHttpRequest
    @GetMapping("/ping")
    public PingResponseDto ping() {
        log.info("Продуктовый сервис на связи");
        return new PingResponseDto(HttpStatus.OK.value(), "Интеграция жива!");
    }

    @PostMapping("/payment/execute")
    public ProductTransactionResponseDto executeTransaction(@RequestBody PaymentRequestDto request) {
        return productService.executeTransaction(request);
    }
}
