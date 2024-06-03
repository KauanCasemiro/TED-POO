package com.example.execution;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExecutorMetodoSeguroImpl implements ExecutorMetodoSeguro {
    private final Object instanciaServico;

    public ExecutorMetodoSeguroImpl(Object instanciaServico) {
        this.instanciaServico = instanciaServico;
    }

    @Override
    public void executar(Method metodo) {
        try {
            metodo.invoke(instanciaServico);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Erro ao invocar o método seguro: " + e.getMessage());
        }
    }
}
