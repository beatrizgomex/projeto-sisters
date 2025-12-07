package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "amizade")
public class Amizade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAmizade;

    @ManyToOne
    @JoinColumn(name = "id_usuaria1", nullable = false)
    private Usuaria usuaria1;

    @ManyToOne
    @JoinColumn(name = "id_usuaria2", nullable = false)
    private Usuaria usuaria2;

    @Column(nullable = false)
    private String status;

}