package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ProgramaAcolhimento")

@Entity

public class ProgramaAcolhimento {
    @Id
    @GeneratedValue
    private long id_programa;

    @Column(name = "dataInicio",  nullable = false)
    private LocalDate dataInicio;

    @Column(name = "dataFim", nullable = false)
    private LocalDate dataFim;


    public long getIdPrograma() {
        return id_programa;
    }
}
