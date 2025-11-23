package com.uniriosi.projeto_sisters.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
public class UsuariaResponse {
    private Long idUsuaria;
    private String nome;
    private String email;
    private String papel;
    private String papelAcolhimento;
    private String bioCurta;
    private Boolean preferenciasPriv;
    private String curso;
    private String semestre;
    private List<String> interesses;
    private List<String> habilidades;
}

