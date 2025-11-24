package com.uniriosi.projeto_sisters.service;

import com.uniriosi.projeto_sisters.exception.BusinessRuleException;
import com.uniriosi.projeto_sisters.infrastructure.entitys.ParticipantesPrograma;
import com.uniriosi.projeto_sisters.infrastructure.entitys.ProgramaAcolhimento;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.ParticipantesProgramaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantesProgramaService {

    private final ParticipantesProgramaRepository repository;

    public ParticipantesProgramaService(ParticipantesProgramaRepository repository) {
        this.repository = repository;
    }

    public List<ParticipantesPrograma> listarTodas() {
        return repository.findAll();
    }

    public ParticipantesPrograma solicitarAcolhimento(
            ProgramaAcolhimento programa,
            Usuaria afilhada,
            Usuaria madrinha
    ) {
        repository.findByProgramaAndAfilhada(programa, afilhada)
                .ifPresent(p -> {
                    throw new BusinessRuleException("Afilhada já possui solicitação neste programa.");
                });

        ParticipantesPrograma participacao = ParticipantesPrograma.builder()
                .programa(programa)
                .afilhada(afilhada)
                .madrinha(madrinha)
                .statusConexao("PENDENTE")
                .build();

        return repository.save(participacao);
    }

    public ParticipantesPrograma atualizarStatus(Long idParticipacao, String novoStatus) {
        ParticipantesPrograma p = repository.findById(idParticipacao)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        p.setStatusConexao(novoStatus);
        return repository.save(p);
    }

    public ParticipantesPrograma aceitarSolicitacao(Long idParticipacao, Usuaria madrinha) {
        final int LIMITE_AFILHADAS = 5;
        final String STATUS_ACEITO = "ACEITA";

        long conexoesAtivas = repository.countByMadrinhaAndStatusConexao(madrinha, STATUS_ACEITO);

        if (conexoesAtivas >= LIMITE_AFILHADAS) {

            throw new RuntimeException("Regra de Negócio violada: Esta Madrinha já atingiu o limite máximo de " + LIMITE_AFILHADAS + " afilhadas aceitas.");
        }

        ParticipantesPrograma p = repository.findById(idParticipacao)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        p.setMadrinha(madrinha);
        p.setStatusConexao(STATUS_ACEITO);

        return repository.save(p);
    }

    public ParticipantesPrograma rejeitarSolicitacao(Long idParticipacao, Usuaria madrinha) {
        ParticipantesPrograma p = repository.findById(idParticipacao)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        p.setMadrinha(madrinha);
        p.setStatusConexao("REJEITADA");

        return repository.save(p);
    }
}