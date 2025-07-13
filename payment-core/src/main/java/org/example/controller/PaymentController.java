package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.aspect.annotation.LogHttpRequest;
import org.example.dto.*;
import org.example.service.IntegrationService;
import org.example.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final IntegrationService integrationService;
    private final PaymentService paymentService;

    @LogHttpRequest
    @GetMapping("/ping-product-service")
    public PingResponseDTO pingProductService() {
        return integrationService.pingProductService();
    }

    @LogHttpRequest
    @GetMapping("/user/{userId}/products")
    public List<ProductResponseDto> getUserProducts(@PathVariable Long userId) {
        return paymentService.getProductsByUser(userId);
    }

    @LogHttpRequest
    @PostMapping("/execute")
    public PaymentResponseDto executePayment(@RequestBody ExecuteRequestDto request) {
        return paymentService.executePayment(request);
    }

    @LogHttpRequest
    @GetMapping("/error400")
    public ResponseErrorDto getError400() {
       return integrationService.testError400();
    }

    @LogHttpRequest
    @GetMapping("/error500")
    public ResponseErrorDto getError500() {
        return integrationService.testError500();
    }
}
