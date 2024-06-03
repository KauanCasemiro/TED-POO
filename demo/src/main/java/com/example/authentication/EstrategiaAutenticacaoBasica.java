package com.example.authentication;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EstrategiaAutenticacaoBasica implements EstrategiaAutenticacao {
    private final Map<String, String> usuarios = new ConcurrentHashMap<>();

    @Override
    public boolean autenticar(String usuario, String senha) {
        String senhaArmazenada = usuarios.get(usuario);
        return senhaArmazenada != null && senhaArmazenada.equals(senha);
    }

    @Override
    public boolean registrar(String usuario, String senha) {
        if (usuarios.containsKey(usuario)) {
            return false;
        } else {
            usuarios.put(usuario, senha);
            return true;
        }
    }
}
