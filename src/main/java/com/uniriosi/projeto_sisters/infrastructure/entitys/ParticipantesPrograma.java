package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ParticipantesPrograma")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipantesPrograma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParticipacao;

    // relacionamento com ProgramaAcolhimento
    @ManyToOne
    @JoinColumn(name = "id_programa", nullable = false)
    private ProgramaAcolhimento programa;

    // relacionamento com Usuaria
    @ManyToOne
    @JoinColumn(name = "id_usuaria", nullable = false)
    private Usuaria usuaria;

    // único atributo próprio
    @Column(nullable = false)
    private String statusConexao;
}
