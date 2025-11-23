package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "mensagem")

@Entity
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensagem;

    @Column(nullable = false)
    private String conteudo;

    @Column(nullable = false)
    private LocalDateTime dataEnvio;

    @Column(nullable = false)
    private boolean statusLeitura;

    @ManyToOne
    @JoinColumn(name = "remetente_id", nullable = false)
    private Usuaria remetente;

    @ManyToOne
    @JoinColumn(name = "destinataria_id", nullable = false)
    private Usuaria destinataria;


}
