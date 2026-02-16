package com.davidabx.legacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LegacySyncApplication {

    // 👇 OLHE AQUI NO SEU VS CODE: O BOTÃO "Run | Debug" VAI APARECER EXATAMENTE NESTA LINHA OCULTA
    public static void main(String[] args) {
        SpringApplication.run(LegacySyncApplication.class, args);
        System.out.println("🚀 Legacy Sync API e FTP Workers iniciados com sucesso!");
    }
}