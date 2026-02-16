package com.davidabx.legacy.service;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Serviço responsável pela comunicação com a nuvem AWS.
 * Dispara eventos de notificação para outros microsserviços da empresa.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AwsIntegrationService {

    // A nova e moderna classe injetada automaticamente pelo Spring Cloud AWS 3.0
    private final SqsTemplate sqsTemplate;

    // Nome da fila que criamos no LocalStack
    private static final String QUEUE_NAME = "legacy-sync-queue";

    /**
     * Envia uma mensagem para a fila SQS avisando que o processamento do dia acabou.
     * * @param fileName Nome do arquivo que foi processado
     * @param totalRecords Quantidade de linhas inseridas no BD (banco de dados)
     */
    public void notifyProcessingCompletion(String fileName, int totalRecords) {
        log.info("Iniciando envio de notificação para a AWS SQS. Fila: {}", QUEUE_NAME);

        // Montamos um corpo de mensagem simples no formato JSON-like para notificar a nuvem
        String messagePayload = String.format(
                "{\"arquivo\": \"%s\", \"totalProcessado\": %d, \"dataProcessamento\": \"%s\"}",
                fileName, totalRecords, LocalDateTime.now().toString()
        );

        try {
            // Envia a mensagem para a fila. O SqsTemplate cuida de toda a complexidade de rede.
            sqsTemplate.send(to -> to
                    .queue(QUEUE_NAME)
                    .payload(messagePayload)
            );

            log.info("Mensagem enviada com sucesso para a fila SQS! Payload: {}", messagePayload);
            
        } catch (Exception e) {
            log.error("Falha ao tentar enviar mensagem para a AWS SQS.", e);
            // Dependendo da regra de negócio, poderíamos lançar uma exceção aqui,
            // mas como é apenas uma notificação, vamos apenas logar o erro para não travar o fluxo principal.
        }
    }
}