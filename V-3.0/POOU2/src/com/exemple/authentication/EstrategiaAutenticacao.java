package com.exemple.authentication;

public interface EstrategiaAutenticacao {
    boolean autenticar(String usuario, String senha);
    void registrar(String usuario, String senha);
}
