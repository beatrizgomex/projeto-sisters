package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "aluna")
@PrimaryKeyJoinColumn(name = "id_usuaria")

@Entity
public class Aluna extends Usuaria{

    @Column(nullable = false)
    private String curso;

    @Column(nullable = false)
    private String semestre;
}
