package com.uniriosi.projeto_sisters.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destinataria_id")
    private Usuaria destinataria;

    @Enumerated(EnumType.STRING)
    private TipoNotificacao tipo;

    private String mensagem;

    private boolean lida = false;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    private Long referenciaId;

    private Long programaId;   // ← OBRIGATÓRIO

    private Long usuariaRelacionadoId;
}
