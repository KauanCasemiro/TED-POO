package com.exemple.controllers;

import com.exemple.annotations.CrossOrigin;
import com.exemple.annotations.PostMapping;
import com.exemple.annotations.RequestMapping;
import com.exemple.annotations.RequestParam;
import com.exemple.annotations.RestController;
import com.exemple.authentication.*;
//import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final EstrategiaAutenticacao estrategiaAutenticacao = new EstrategiaAutenticacaoBasica();

    @PostMapping("/register")
    public Map<String, String> register(@RequestParam String username, @RequestParam String password) {
        estrategiaAutenticacao.registrar(username, password);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário registrado com sucesso");
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        Map<String, String> response = new HashMap<>();
        if (estrategiaAutenticacao.autenticar(username, password)) {
            response.put("message", "Login bem-sucedido");
        } else {
            response.put("message", "Falha na autenticação");
        }
        return response;
    }
}
