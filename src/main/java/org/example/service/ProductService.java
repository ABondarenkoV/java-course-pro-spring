package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BalanceResponseDto;
import org.example.dto.ProductResponseDto;
import org.example.entity.Product;
import org.example.exception.EntityNotFoundException;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService  {
    private final ProductRepository productRepository;

    public List<ProductResponseDto> getAllProducts() {
        log.info("Получение общего списка продуктов");
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getAccountNumber(),
                        product.getProductType()))
                .collect(Collectors.toList());
    }
    public List<ProductResponseDto> getProductsByUserId(Long userId) {
        log.info("Получение продуктов для пользователя с ID: {}", userId);
        List<Product> products = productRepository.findAllByUserId(userId);

        return products.stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getAccountNumber(),
                        product.getProductType()))
                .collect(Collectors.toList());
    }
    public ProductResponseDto getProductById(long productId) {
        log.info("Получение продукта по ID : {}", productId);

        return productRepository.findById(productId)
                .map(product -> new ProductResponseDto(product.getId(), product.getAccountNumber(), product.getProductType()))
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден с  ID: " + productId));

    }

    public BalanceResponseDto getBalanceByUserId(Long userId) {
        log.info("Запрос баланса по идентификатору пользователя: {}", userId);

        return productRepository.findById(userId)
                .map(product -> new BalanceResponseDto(product.getAccountNumber(),product.getBalance()))
                .orElseThrow(()->new EntityNotFoundException("Баланс не найден по ID пользователя: " + userId ));
    }

}