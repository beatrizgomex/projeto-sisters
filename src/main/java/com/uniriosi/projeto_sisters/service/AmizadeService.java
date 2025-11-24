package com.uniriosi.projeto_sisters.service;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Amizade;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.AmizadeRepository;
import com.uniriosi.projeto_sisters.infrastructure.repository.UsuariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AmizadeService {
    @Autowired
    private UsuariaRepository usuariaRepository;

    @Autowired
    private AmizadeRepository amizadeRepository;



    public AmizadeService(AmizadeRepository amizadeRepository, UsuariaRepository usuariaRepository) {
        this.amizadeRepository = amizadeRepository;
        this.usuariaRepository = usuariaRepository;
    }

    public Amizade solicitarAmizade(Long idSolicitante, Long idSolicitada) {
        Usuaria solicitante = usuariaRepository.findById(idSolicitante)
                .orElseThrow(() -> new RuntimeException("Usuária solicitante não encontrada"));
        Usuaria solicitada = usuariaRepository.findById(idSolicitada)
                .orElseThrow(() -> new RuntimeException("Usuária solicitada não encontrada"));

        Optional<Amizade> amizadeExistente = amizadeRepository.findByUsuaria1AndUsuaria2OrUsuaria2AndUsuaria1(
                solicitante, solicitada, solicitada, solicitante);

        if (amizadeExistente.isPresent()) {
            throw new RuntimeException("Já existe um vínculo (pendente, aceito ou recusado) entre estas usuárias.");
        }

        Amizade novaAmizade = Amizade.builder()
                .usuaria1(solicitante)
                .usuaria2(solicitada)
                .status("PENDENTE")
                .build();

        return amizadeRepository.save(novaAmizade);
    }

    public Amizade aceitarAmizade(Long idAmizade, Long idUsuaria) {
        Amizade amizade = amizadeRepository.findById(idAmizade)
                .orElseThrow(() -> new RuntimeException("Solicitação de amizade não encontrada"));

        if (!amizade.getUsuaria2().getIdUsuaria().equals(idUsuaria)) {
            throw new RuntimeException("Você não tem permissão para aceitar esta solicitação.");
        }

        amizade.setStatus("ACEITO");
        return amizadeRepository.save(amizade);
    }

    public Amizade recusarAmizade(Long idAmizade, Long idUsuaria) {
        Amizade amizade = amizadeRepository.findById(idAmizade)
                .orElseThrow(() -> new RuntimeException("Solicitação de amizade não encontrada"));

        if (!amizade.getUsuaria2().getIdUsuaria().equals(idUsuaria)) {
            throw new RuntimeException("Você não tem permissão para recusar esta solicitação.");
        }

        amizade.setStatus("RECUSADO");
        return amizadeRepository.save(amizade);
    }

    public boolean saoAmigas(Long idUsuaria1, Long idUsuaria2) {
        Usuaria u1 = usuariaRepository.findById(idUsuaria1).orElse(null);
        Usuaria u2 = usuariaRepository.findById(idUsuaria2).orElse(null);

        if (u1 == null || u2 == null) return false;

        Optional<Amizade> amizade = amizadeRepository.findByUsuaria1AndUsuaria2OrUsuaria2AndUsuaria1(
                u1, u2, u1, u2);

        return amizade.isPresent() && amizade.get().getStatus().equals("ACEITO");
    }
    public List<Usuaria> listarTodasAmigas(Long idUsuaria) {
        Usuaria usuariaAlvo = usuariaRepository.findById(idUsuaria)
                .orElseThrow(() -> new RuntimeException("Usuária não encontrada"));

        List<Amizade> amizadesAceitas = amizadeRepository.findAllAcceptedFriends(usuariaAlvo, "ACEITO");

        List<Usuaria> listaAmigas = new ArrayList<>();

        for (Amizade amizade : amizadesAceitas) {
            if (amizade.getUsuaria1().getIdUsuaria().equals(idUsuaria)) {
                listaAmigas.add(amizade.getUsuaria2());
            } else {
                listaAmigas.add(amizade.getUsuaria1());
            }
        }

        return listaAmigas;
    }
}
