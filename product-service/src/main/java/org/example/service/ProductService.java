package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BalanceResponseDto;
import org.example.dto.PaymentRequestDto;
import org.example.dto.ProductResponseDto;
import org.example.dto.ProductTransactionResponseDto;
import org.example.entity.Product;
import org.example.exception.EntityNotFoundException;
import org.example.exception.InsufficientFundsException;
import org.example.exception.InvalidOperationException;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    //Subtract и add
    @Transactional
    public ProductTransactionResponseDto executeTransaction(PaymentRequestDto request) {
        log.info("Выполнение транзакций , операция : {}" ,request.operation());

        Product product = productRepository.findByIdAndUserId(request.productId(), request.userId())
                .orElseThrow(() -> new EntityNotFoundException("Продукт ID : " + request.productId()+ " не найден у данного пользователя ID : " + request.userId()));


        switch (request.operation()) {
            case "ADD":
                log.info("Выполнение операции : {} | на сумму : {} " ,request.operation(), request.amount());
                product.setBalance(product.getBalance().add(request.amount()));
                break;
            case "SUBTRACT":
                log.info("Выполнение операции : {} | на сумму : {} " ,request.operation(), request.amount());
                if (product.getBalance().compareTo(request.amount()) < 0) {
                    throw new InsufficientFundsException("Недостаточно средств на продукте с ID : " + product.getId());
                }
                product.setBalance(product.getBalance().subtract(request.amount()));
                break;
            default:
                throw new InvalidOperationException("Неверная операция");
        }

        productRepository.save(product);
        return new ProductTransactionResponseDto(
                product.getId(),
                product.getAccountNumber(),
                product.getBalance(),
                product.getProductType(),
                LocalDateTime.now(),
                "Транзакция "+ request.operation()+ " успешна!"
        );
    }
}