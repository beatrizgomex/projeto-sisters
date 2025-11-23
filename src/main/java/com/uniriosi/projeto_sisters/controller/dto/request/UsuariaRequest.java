package com.uniriosi.projeto_sisters.controller.dto.request;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UsuariaRequest {
    private String nome;
    private String email;
    private String senha;
    private String papel;
    private String papelAcolhimento;
    private String bioCurta;
    private Boolean preferenciasPriv;
    private List<String> interesses;
    private List<String> habilidades;
    private String curso;
    private String semestre;
}
