package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "aluna")

@Entity
public class Aluna extends Usuaria{

    @Column(nullable = false)
    private String curso;

    @Column(nullable = false)
    private String semestre;
}
