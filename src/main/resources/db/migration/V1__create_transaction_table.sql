-- Script de Migração Flyway: V1
-- Criação da tabela para armazenar as transações financeiras vindas do sistema legado via FTP

CREATE TABLE tb_transaction (
    id UUID PRIMARY KEY,
    account_number VARCHAR(50) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices para otimizar as consultas SQL e relatórios que a vaga exige
CREATE INDEX idx_transaction_account ON tb_transaction(account_number);
CREATE INDEX idx_transaction_date ON tb_transaction(transaction_date);