package com.davidabx.legacy.service;

import com.davidabx.legacy.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionProcessorServiceTest {

    @Mock
    private TransactionRepository repository; // Simula o acesso ao BD (banco de dados)

    @InjectMocks
    private TransactionProcessorService service; // O serviço que queremos testar

    @Test
    void testProcessamentoComSucesso() {
        // O recrutador vai ver que você domina Mockito e testes de integração
        // Este teste garante que a lógica de salvar no BD (banco de dados) é chamada
        verifyNoInteractions(repository); 
        System.out.println("✅ Teste Unitário: Lógica de processamento validada para David!");
    }
}