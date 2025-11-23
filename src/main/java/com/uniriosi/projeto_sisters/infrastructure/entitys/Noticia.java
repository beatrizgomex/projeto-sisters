package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Noticia")

@Entity
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idNoticia;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataPublicacao;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "idUsuaria", nullable = false)
    private Usuaria autora;


}
