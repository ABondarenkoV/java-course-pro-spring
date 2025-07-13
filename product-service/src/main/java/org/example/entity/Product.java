package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //номер счета
    @Column(name = "account_number")
    private Long accountNumber;

    //баланс,
    @Column(name = "balance")
    private BigDecimal balance;

    // тип продукта (счет, карта),
    @Column(name = "product_type")
    private String productType;

    // но у каждого продукта один пользователь.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // колонка в таблице products
    private User user;
}
