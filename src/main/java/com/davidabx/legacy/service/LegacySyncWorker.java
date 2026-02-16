package com.davidabx.legacy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Robô agendador (Worker) responsável por executar a sincronização 
 * de forma 100% automática, sem intervenção humana.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LegacySyncWorker {

    // Injetando os 3 serviços que criamos anteriormente
    private final FtpIntegrationService ftpService;
    private final TransactionProcessorService processorService;
    private final AwsIntegrationService awsService;

    /**
     * Expressão Cron: Define de quanto em quanto tempo o robô vai acordar.
     * "0 * * * * ?" significa: Executar a cada 1 minuto (ideal para testarmos agora).
     * Em produção corporativa, seria algo como "0 0 2 * * ?" (Executar às 2h da manhã).
     */
    @Scheduled(cron = "0 * * * * ?")
    public void executeDailySync() {
        log.info(">>> INICIANDO ROTINA AUTOMÁTICA DE SINCRONIZAÇÃO LEGADA <<<");
        
        // Nome do arquivo que o sistema legado supostamente gera todos os dias
        String remoteFileName = "transacoes.csv"; 

        try {
            // Passo 1: Conecta no servidor antigo e baixa o arquivo
            File downloadedFile = ftpService.downloadFileFromLegacySystem(remoteFileName);

            // Passo 2: Processa o CSV e salva no nosso PostgreSQL
            processorService.processCsvFile(downloadedFile);

            // Passo 3: Avisa a AWS que o lote do dia terminou
            // Nota Sênior: Estamos passando '100' como exemplo. Numa versão final, 
            // faríamos o processorService retornar a quantidade exata de linhas salvas.
            awsService.notifyProcessingCompletion(downloadedFile.getName(), 100);

            log.info(">>> ROTINA AUTOMÁTICA FINALIZADA COM SUCESSO <<<");

        } catch (Exception e) {
            log.error(">>> ERRO FATAL DURANTE A SINCRONIZAÇÃO: {} <<<", e.getMessage());
        }
    }
}