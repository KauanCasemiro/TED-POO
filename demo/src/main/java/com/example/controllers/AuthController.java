package com.example.controllers;

import com.example.authentication.EstrategiaAutenticacao;
import com.example.authentication.EstrategiaAutenticacaoBasica;
import com.example.authentication.ManipuladorAutenticacao;
import com.example.execution.ExecutorMetodoSeguroImpl;
import com.example.services.ServicoSeguro;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final EstrategiaAutenticacao estrategiaAutenticacao = new EstrategiaAutenticacaoBasica();

    @PostMapping("/register")
    public Map<String, String> register(@RequestParam String username, @RequestParam String password) {
        Map<String, String> response = new HashMap<>();
        if (estrategiaAutenticacao.registrar(username, password)) {
            response.put("message", "Usuário registrado com sucesso.");
        } else {
            response.put("message", "Usuário já existe.");
        }
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        ManipuladorAutenticacao manipuladorAutenticacao = new ManipuladorAutenticacao(estrategiaAutenticacao, new ExecutorMetodoSeguroImpl(new ServicoSeguro()));
        Map<String, String> response = new HashMap<>();
        if (estrategiaAutenticacao.autenticar(username, password)) {
            manipuladorAutenticacao.executarMetodoSeguro(username, password, new ServicoSeguro());
            response.put("message", "Login bem-sucedido.");
        } else {
            response.put("message", "Falha na autenticação. Acesso negado.");
        }
        return response;
    }
}
