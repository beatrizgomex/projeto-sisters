package com.uniriosi.projeto_sisters.service;

import com.uniriosi.projeto_sisters.exception.ProgramaNaoEncontradoException;
import com.uniriosi.projeto_sisters.infrastructure.entitys.ParticipantesPrograma;
import com.uniriosi.projeto_sisters.infrastructure.entitys.ProgramaAcolhimento;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.ParticipantesProgramaRepository;
import org.springframework.stereotype.Service;
import com.uniriosi.projeto_sisters.infrastructure.repository.ProgramaAcolhimentoRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service

public class ProgramaAcolhimentoService {
        private final ProgramaAcolhimentoRepository programaRepository;
        private final ParticipantesProgramaRepository participantesRepository;

        public ProgramaAcolhimentoService(ProgramaAcolhimentoRepository programaRepository,
                                          ParticipantesProgramaRepository participantesRepository) {
            this.programaRepository = programaRepository;
            this.participantesRepository = participantesRepository;
        }

        public void solicitarAcolhimento(Long idPrograma, Usuaria acolhida) {
            try {
                // Tenta buscar o programa pelo ID
                ProgramaAcolhimento programa = programaRepository.findById(idPrograma).get();

                // Verifica se a usuária já participa do programa
                Optional<ParticipantesPrograma> existente =
                        participantesRepository.findByProgramaAndUsuaria(programa, acolhida);

                if (existente.isPresent()) {
                    System.out.println("A usuária já está participando deste programa.");
                    return;
                }

                // Cria a relação acolhida-programa com status pendente
                ParticipantesPrograma participacao = ParticipantesPrograma.builder()
                        .programa(programa)
                        .usuaria(acolhida)
                        .statusConexao("PENDENTE")
                        .build();

                participantesRepository.save(participacao);
                System.out.println("Solicitação de acolhimento enviada por " + acolhida.getNome());

            } catch (NoSuchElementException e) {
                // Esse bloco é executado se o programa NÃO for encontrado no banco
                System.out.println("Erro: Programa de acolhimento com ID " + idPrograma + " não encontrado!");
            }
        }


        /*
        public void aceitarOuRejeitar(Usuaria madrinha, Usuaria acolhida, boolean aceitar) {
            // Procura o relacionamento entre as usuárias dentro desse programa
            for (ParticipantesPrograma relacao : participantes) {
                if (relacao.getUsuaria().equals(acolhida)) {
                    if (aceitar) {
                        relacao.setStatusConexao("ACEITA");
                        System.out.println("Acolhimento aceito por "
                                + madrinha.getNome() + " para " + acolhida.getNome());
                    } else {
                        relacao.setStatusConexao("REJEITADA");
                        System.out.println("Acolhimento rejeitado por "
                                + madrinha.getNome() + " para " + acolhida.getNome());
                    }
                    return; // Sai do loop após encontrar a usuária
                }
            }

            // Caso não encontre nenhuma relação correspondente
            System.out.println("Nenhuma relação encontrada entre "
                    + madrinha.getNome() + " e " + acolhida.getNome()
                    + " neste programa.");
        }

         */


        /*
        public void sugerirMadrinha(Long idPrograma, Usuaria madrinha) {
            ProgramaAcolhimento programa = repository.findById(idPrograma)
                    .orElseThrow(() -> new ProgramaNaoEncontradoException(idPrograma));

            System.out.println("Sugestão de madrinha para o programa "
                    + programa.getIdPrograma() + ": " + madrinha.getNome());
        }

         */
    }


