package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aluna")

@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "id_usuaria")
public class Aluna extends Usuaria{

    @Id
    @Column(name = "id_usuaria") // Nome da coluna na tabela 'aluna'
    private Long idUsuaria;

    @Column(nullable = false)
    private String curso;

    @Column(nullable = false)
    private String semestre;
}
