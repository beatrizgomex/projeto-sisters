package com.uniriosi.projeto_sisters.controller;

import com.uniriosi.projeto_sisters.infrastructure.entitys.ParticipantesPrograma;
import com.uniriosi.projeto_sisters.service.ParticipantesProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participacoes")
@CrossOrigin("*")
public class ParticipantesProgramaController {

    @Autowired
    private ParticipantesProgramaService participantesService;

    @GetMapping("/{idParticipacao}")
    public ParticipantesPrograma buscarPorId(@PathVariable Long idParticipacao) {
        return participantesService.listarTodas().stream()
                .filter(p -> p.getIdParticipacao().equals(idParticipacao))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));
    }


    @PatchMapping("/{idParticipacao}")
    public ParticipantesPrograma atualizarStatus(
            @PathVariable Long idParticipacao,
            @RequestParam String status
    ) {
        return participantesService.atualizarStatus(idParticipacao, status);
    }

    @DeleteMapping("/{idParticipacao}")
    public void excluir(@PathVariable Long idParticipacao) {
        participantesService.excluirParticipacao(idParticipacao);
    }
}