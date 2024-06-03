package com.example.authentication;

public interface EstrategiaAutenticacao {
    boolean autenticar(String usuario, String senha);
    boolean registrar(String usuario, String senha);
}
