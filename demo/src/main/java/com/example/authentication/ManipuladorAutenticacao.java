package com.example.authentication;

import java.lang.reflect.Method;
import com.example.execution.*;
import com.example.annotations.*;

public class ManipuladorAutenticacao {
    private final EstrategiaAutenticacao estrategiaAutenticacao;
    private final ExecutorMetodoSeguro executorMetodoSeguro;

    public ManipuladorAutenticacao(EstrategiaAutenticacao estrategiaAutenticacao, ExecutorMetodoSeguro executorMetodoSeguro) {
        this.estrategiaAutenticacao = estrategiaAutenticacao;
        this.executorMetodoSeguro = executorMetodoSeguro;
    }

    public void executarMetodoSeguro(String usuario, String senha, Object instanciaServico) {
        if (estrategiaAutenticacao.autenticar(usuario, senha)) {
            Method[] metodos = instanciaServico.getClass().getMethods();
            for (Method metodo : metodos) {
                if (metodo.isAnnotationPresent(RequerAutenticacao.class)) {
                    executorMetodoSeguro.executar(metodo);
                }
            }
        } else {
            System.out.println("Falha na autenticação. Acesso negado.");
        }
    }
}
