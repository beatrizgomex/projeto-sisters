package com.uniriosi.projeto_sisters.dto;

public record ParticipacaoResponse(
        Long idParticipacao,
        Long idPrograma,
        Long idAfilhada,
        Long idMadrinha,
        String status
) {}
