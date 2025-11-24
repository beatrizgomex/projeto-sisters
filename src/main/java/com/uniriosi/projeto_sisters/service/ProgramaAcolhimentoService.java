package com.uniriosi.projeto_sisters.service;

import com.uniriosi.projeto_sisters.exception.ResourceNotFoundException;
import com.uniriosi.projeto_sisters.infrastructure.entitys.ParticipantesPrograma;
import com.uniriosi.projeto_sisters.infrastructure.entitys.ProgramaAcolhimento;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.ParticipantesProgramaRepository; // Import necessário
import com.uniriosi.projeto_sisters.infrastructure.repository.ProgramaAcolhimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramaAcolhimentoService {

    @Autowired
    private ProgramaAcolhimentoRepository programaAcolhimentoRepository;

    @Autowired
    private ParticipantesProgramaRepository participantesProgramaRepository; // Agora injetando o ajustado

    @Autowired
    private UsuariaService usuariaService;

    private static final int MAX_AFILHADAS_POR_MADRINHA = 5;

    public ProgramaAcolhimento salvarPrograma(ProgramaAcolhimento programa) {
        return programaAcolhimentoRepository.save(programa);
    }

    public List<ProgramaAcolhimento> listarProgramas() {
        return programaAcolhimentoRepository.findAll();
    }

    public ProgramaAcolhimento buscarPorId(Long idPrograma) {
        return programaAcolhimentoRepository.findById(idPrograma)
                .orElseThrow(() -> new ResourceNotFoundException("Programa de Acolhimento não encontrado com ID: " + idPrograma));
    }

    public List<ParticipantesPrograma> listarParticipantes(Long idPrograma) {
        ProgramaAcolhimento programa = buscarPorId(idPrograma);
        return participantesProgramaRepository.findByPrograma(programa);
    }

    public List<Usuaria> sugerirMadrinhasPara(Long idPrograma, Long afilhadaId) {

        Usuaria afilhada = usuariaService.buscarPorId(afilhadaId);

        if (participantesProgramaRepository.findAcceptedConnectionByMentee(afilhada).isPresent()) {
            return List.of();
        }

        List<Usuaria> candidatasMadrinha = usuariaService.findMadrinhaVeteranaCandidates();

        List<Usuaria> madrinhasSugeridas = candidatasMadrinha.stream()
                .filter(madrinha -> {
                    // Usa o método countAcceptedMenteesByMentor(madrinha) do repositório.
                    Long afilhadasAtivas = participantesProgramaRepository.countAcceptedMenteesByMentor(madrinha);
                    return afilhadasAtivas < MAX_AFILHADAS_POR_MADRINHA;
                })
                .collect(Collectors.toList());

        return madrinhasSugeridas;
    }
}
