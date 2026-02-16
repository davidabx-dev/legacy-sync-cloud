package com.davidabx.legacy.repository;

import com.davidabx.legacy.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Interface responsável pela comunicação com o BD (banco de dados).
 * Abstrai as consultas SQL complexas e relatórios exigidos pelo sistema.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    // 1. Query Method Automático do Spring Data (Busca por status)
    List<Transaction> findByStatus(String status);

    // 2. Relatório Customizado: Busca transações por período de data usando JPQL
    @Query("SELECT t FROM Transaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findTransactionsForReport(
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate
    );

    // 3. Relatório Customizado com SQL Nativo: Exemplo de agregação de valores
    // Ideal para mostrar ao recrutador que você domina SQL raiz e performance
    @Query(value = "SELECT status, COUNT(*) as total_transactions, SUM(amount) as total_amount " +
                   "FROM tb_transaction GROUP BY status", nativeQuery = true)
    List<Object[]> generateFinancialSummaryReport();
}