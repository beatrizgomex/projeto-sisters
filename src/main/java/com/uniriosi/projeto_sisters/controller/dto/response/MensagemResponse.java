package com.uniriosi.projeto_sisters.controller.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MensagemResponse {
    private Long idMensagem;
    private String conteudo;
    private LocalDateTime dataEnvio;
    private Long remetenteId;
    private Long destinatariaId;
}
