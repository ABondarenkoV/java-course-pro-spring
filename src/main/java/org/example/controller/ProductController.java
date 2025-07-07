package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.aspect.annotation.LogHttpRequest;
import org.example.dto.BalanceResponseDto;
import org.example.dto.ProductResponseDto;
import org.example.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
