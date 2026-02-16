package com.davidabx.legacy.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Serviço responsável por conectar ao servidor legado via FTP,
 * baixar o arquivo de transações financeiras e disponibilizá-lo para processamento.
 */
@Slf4j // Lombok: Injeta automaticamente um objeto de log (logger) na classe
@Service
public class FtpIntegrationService {

    // Injetando as variáveis que configuramos no nosso application.yml
    @Value("${legacy.ftp.server}")
    private String server;

    @Value("${legacy.ftp.port}")
    private int port;

    @Value("${legacy.ftp.username}")
    private String username;

    @Value("${legacy.ftp.password}")
    private String password;

    /**
     * Conecta no servidor legado e faz o download de um arquivo específico.
     * * @param remoteFilePath O nome do arquivo lá no servidor (ex: "transacoes_do_dia.csv")
     * @return Um objeto File apontando para o arquivo baixado temporariamente na sua máquina
     */
    public File downloadFileFromLegacySystem(String remoteFilePath) {
        FTPClient ftpClient = new FTPClient();
        File downloadedFile = null;

        try {
            log.info("Iniciando conexão com o servidor FTP legado em {}:{}", server, port);
            ftpClient.connect(server, port);
            
            boolean loginSuccess = ftpClient.login(username, password);
            if (!loginSuccess) {
                throw new RuntimeException("Falha na autenticação do FTP. Verifique usuário e senha.");
            }

            // Configuração OBRIGATÓRIA para evitar travamentos de firewall no modo passivo
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // Garante que não haverá corrupção de dados

            log.info("Conexão bem-sucedida! Baixando o arquivo: {}", remoteFilePath);

            // Cria um arquivo temporário no sistema operacional do seu computador
            downloadedFile = File.createTempFile("legacy_sync_", ".csv");
            
            try (FileOutputStream fos = new FileOutputStream(downloadedFile)) {
                boolean downloadSuccess = ftpClient.retrieveFile(remoteFilePath, fos);
                if (!downloadSuccess) {
                    throw new RuntimeException("Arquivo não encontrado no servidor legado: " + remoteFilePath);
                }
            }

            log.info("Download concluído com sucesso. Arquivo salvo temporariamente em: {}", downloadedFile.getAbsolutePath());

        } catch (IOException e) {
            log.error("Erro de I/O ao tentar comunicar com o FTP: {}", e.getMessage(), e);
            throw new RuntimeException("Erro na integração via FTP", e);
        } finally {
            // Prática Sênior: SEMPRE fechar a conexão, mesmo se ocorrer um erro na metade do processo
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                    log.info("Conexão FTP encerrada com segurança.");
                }
            } catch (IOException ex) {
                log.error("Erro ao tentar desconectar do FTP.", ex);
            }
        }

        return downloadedFile;
    }
}