package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.BalanceResponseDto;
import org.example.dto.ProductResponseDto;
import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService  {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
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
    public Optional<ProductResponseDto> getProductById(long productId) {
        log.info("Получение продукта по ID : {}", productId);

        return productRepository.findById(productId)
                .map(product -> {
                    log.info("Найден продукт с ID: {}", product.getId());
                    return new ProductResponseDto(product.getId(), product.getAccountNumber(), product.getProductType());
                });
    }

    public Optional<BalanceResponseDto> getBalanceByUserId(Long userId) {
        log.info("Запрос баланса по идентификатору пользователя: {}", userId);

        return productRepository.findById(userId)
                .map(product -> {
                    log.info("Найден баланс с ID: {}", product.getId());
                    return new BalanceResponseDto(product.getAccountNumber(),product.getBalance());
                });
    }

}
