package com.example.services;
import com.example.annotations.*;
import com.example.authentication.*;

public class ServicoSeguro {
    @RequerAutenticacao
    public void metodoSeguro() {
        System.out.println("Método seguro executado com sucesso!");
    }
}
