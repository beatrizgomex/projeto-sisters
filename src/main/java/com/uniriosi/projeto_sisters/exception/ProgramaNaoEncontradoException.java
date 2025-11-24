package com.uniriosi.projeto_sisters.exception;

public class ProgramaNaoEncontradoException extends RuntimeException {
    public ProgramaNaoEncontradoException(Long id) {
        super("Programa de acolhimento com ID " + id + " não encontrado.");
    }
}



