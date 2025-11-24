package com.uniriosi.projeto_sisters.controller.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class UsuariaUpdateRequest {
    private String nome;
    private String bioCurta;
    private String senha;
    private String papel;
    private String papelAcolhimento;
    private Boolean preferenciasPriv;
    private List<String> interesses;
    private List<String> habilidades;
    private String semestre;
}
