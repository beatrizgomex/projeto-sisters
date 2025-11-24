package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "pergunta")
@Entity

public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPergunta;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String corpo;

    @Column(nullable = false)
    private LocalDateTime dataPublicacao;

    @ManyToOne
    @JoinColumn(name = "usuaria_id", nullable = false)
    private Usuaria autora;

    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Resposta> respostas;
}
