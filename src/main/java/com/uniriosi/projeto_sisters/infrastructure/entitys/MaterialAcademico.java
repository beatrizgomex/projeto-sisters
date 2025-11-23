package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MaterialAcademico")
@Entity
public class MaterialAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMaterial;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    @Column
    private String tipo;

    @Column(length = 300) //
    private String linkArquivo;

    @Column
    private String status;


    @ManyToOne
    @JoinColumn(name = "idUsuaria", nullable = false) // Chave estrangeira do SQL
    private Usuaria autora;
}
