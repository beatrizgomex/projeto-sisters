package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "participantes_programa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipantesPrograma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParticipacao;


    @ManyToOne
    @JoinColumn(name = "id_programa", nullable = false)
    private ProgramaAcolhimento programa;


    @ManyToOne
    @JoinColumn(name = "id_afilhada", nullable = false)
    private Usuaria afilhada;

    @ManyToOne
    @JoinColumn(name = "id_madrinha")
    private Usuaria madrinha;

    @Column(nullable = false)
    private String statusConexao;
}