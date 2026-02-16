package com.davidabx.legacy.controller;

import com.davidabx.legacy.repository.TransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {

    private final TransactionRepository repository;

    public TransactionController(TransactionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/relatorio")
    public String exibirRelatorio(Model model) {
        // Busca todos os dados do BD (banco de dados) e envia para a tela
        model.addAttribute("transacoes", repository.findAll());
        return "relatorio"; // Nome do arquivo JSP
    }
}