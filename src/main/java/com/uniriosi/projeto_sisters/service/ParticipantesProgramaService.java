package com.uniriosi.projeto_sisters.service;

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

    // Criar nova participação (pode começar com status "PENDENTE")
    public ParticipantesPrograma adicionarParticipante(ParticipantesPrograma participante) {
        if (participante.getStatusConexao() == null) {
            participante.setStatusConexao("PENDENTE");
        }
        return repository.save(participante);
    }

    // Atualizar o status de uma participação existente
    public ParticipantesPrograma atualizarStatus(Long idParticipacao, String novoStatus) {
        ParticipantesPrograma participante = repository.findById(idParticipacao)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));
        participante.setStatusConexao(novoStatus);
        return repository.save(participante);
    }

}
