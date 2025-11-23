package com.uniriosi.projeto_sisters.controller.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemRequest {

    private Long idRemetente;
    private Long idDestinataria;
    private String conteudo;
}