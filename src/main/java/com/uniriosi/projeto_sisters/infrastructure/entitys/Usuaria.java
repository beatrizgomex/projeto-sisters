package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usuaria")

@Entity
public class Usuaria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUsuaria;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, name = "email", unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String papel;

    @Column(nullable = false)
    private String papelAcolhimento;

    @Column(nullable = false)
    private String bioCurta;

    @ElementCollection
    @CollectionTable(name = "usuaria_interesses", joinColumns = @JoinColumn(name = "id_usuaria"))
    @Column(name = "interesse")
    private List<String> interesses;

    @ElementCollection
    @CollectionTable(name = "usuaria_habilidades", joinColumns = @JoinColumn(name = "id_usuaria"))
    @Column(name = "habilidades")
    private List<String> habilidades;

    @Column(nullable = false)
    private boolean preferenciasPriv= true;

    @ManyToMany
    @JoinTable(name = "amizade",
            joinColumns = @JoinColumn(name = "id_usuaria1"),
            inverseJoinColumns = @JoinColumn(name = "id_usuaria2")
    )
    private List<Usuaria> listaAmigas;


}
