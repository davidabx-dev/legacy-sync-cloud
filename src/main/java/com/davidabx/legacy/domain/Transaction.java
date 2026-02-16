package com.davidabx.legacy.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade que representa uma transação financeira importada do sistema legado.
 */
@Entity
@Table(name = "tb_transaction")
@Data // Lombok: Gera Getters, Setters, toString, equals e hashCode automaticamente
@NoArgsConstructor // Lombok: Exigência da JPA para criar a classe vazia nos bastidores
@AllArgsConstructor // Lombok: Cria um construtor com todos os atributos
@Builder // Lombok: Padrão de projeto Sênior para criar objetos de forma fluente
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "account_number", nullable = false, length = 50)
    private String accountNumber;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Método de callback da JPA executado automaticamente antes de salvar no BD (banco de dados)
     * Garante que a data de criação seja sempre preenchida sem precisarmos fazer isso manualmente no código.
     */
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}