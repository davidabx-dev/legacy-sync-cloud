package com.davidabx.legacy.service;

import com.davidabx.legacy.domain.Transaction;
import com.davidabx.legacy.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável por ler o arquivo CSV (Comma-Separated Values) (Valores Separados por Vírgula),
 * transformar as linhas de texto em objetos Java e salvar no BD (banco de dados).
 */
@Slf4j
@Service
@RequiredArgsConstructor // Lombok: Cria o construtor automaticamente injetando o repository
public class TransactionProcessorService {

    private final TransactionRepository repository;

    /**
     * Processa o arquivo linha por linha e salva no banco.
     * A anotação @Transactional garante que, se ocorrer um erro na linha 1000, 
     * nenhuma das 999 anteriores será salva, mantendo a integridade dos dados.
     */
    @Transactional
    public void processCsvFile(File file) {
        log.info("Iniciando o processamento do arquivo extraído: {}", file.getName());
        
        // Lista temporária para salvar os dados em lote (Batch Insert)
        List<Transaction> transactionsToSave = new ArrayList<>();

        // Try-with-resources: O Java fecha o 'BufferedReader' automaticamente no final
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;

            // Lê o arquivo linha por linha até o final
            while ((line = br.readLine()) != null) {
                
                // Pula a primeira linha se for o cabeçalho das colunas
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; 
                }

                // Divide a linha usando a vírgula como separador
                String[] columns = line.split(",");
                
                // Valida se a linha tem o número correto de colunas (conta, valor, data, status)
                if (columns.length >= 4) {
                    Transaction transaction = Transaction.builder()
                            .accountNumber(columns[0].trim())
                            .amount(new BigDecimal(columns[1].trim()))
                            .transactionDate(LocalDateTime.parse(columns[2].trim()))
                            .status(columns[3].trim())
                            .build();

                    transactionsToSave.add(transaction);
                }
            }

            // Salva todas as transações de uma só vez no BD (banco de dados)
            repository.saveAll(transactionsToSave);
            log.info("Processamento concluído com sucesso. {} transações armazenadas no BD (banco de dados).", transactionsToSave.size());

        } catch (IOException | RuntimeException e) {
            log.error("Falha crítica ao tentar processar o arquivo de texto.", e);
            throw new RuntimeException("Erro ao processar as transações do sistema legado", e);
        }
    }
}