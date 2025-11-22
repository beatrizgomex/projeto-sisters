package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "resposta")
@Entity

public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idResposta;

    @Column(nullable = false)
    private String corpo;

    @Column(nullable = false)
    private LocalDateTime dataPublicacao;

    @Column(nullable = false)
    private boolean aceita = false;

    @ManyToOne
    @JoinColumn(name = "usuaria_id", nullable = false)
    private Usuaria autora;

    @ManyToOne
    @JoinColumn(name = "pergunta_id", nullable = false)
    private Pergunta pergunta;


}
