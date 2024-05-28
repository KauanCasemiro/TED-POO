package com.exemple.services;

import com.exemple.annotations.*;

public class ServicoSeguro {
    @com.exemple.annotations.RequerAutenticacao
    public void metodoSeguro() {
        System.out.println("Método seguro executado com sucesso!");
    }
}
