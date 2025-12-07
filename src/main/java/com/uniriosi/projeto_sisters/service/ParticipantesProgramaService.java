package com.uniriosi.projeto_sisters.service;

import com.uniriosi.projeto_sisters.infrastructure.entitys.ParticipantesPrograma;
import com.uniriosi.projeto_sisters.infrastructure.entitys.ProgramaAcolhimento;
import com.uniriosi.projeto_sisters.infrastructure.entitys.TipoNotificacao;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.ParticipantesProgramaRepository;
import org.springframework.stereotype.Service;
import com.uniriosi.projeto_sisters.service.NotificacaoService;

import java.util.List;

@Service
public class ParticipantesProgramaService {

    private final ParticipantesProgramaRepository repository;

    private final NotificacaoService notificacaoService;

    public ParticipantesProgramaService(ParticipantesProgramaRepository repository, NotificacaoService notificacaoService) {
        this.repository = repository;
        this.notificacaoService = notificacaoService;
    }

    public List<ParticipantesPrograma> listarTodas() {
        return repository.findAll();
    }

    public ParticipantesPrograma solicitarAcolhimento(
            ProgramaAcolhimento programa,
            Usuaria afilhada,
            Usuaria madrinhaSugestao
    ) {

        // Regra 1: afilhada já tem madrinha?
        boolean afilhadaJaTemMadrinha = repository
                .existsByProgramaAndAfilhadaAndStatusConexao(programa, afilhada, "ACEITA");

        if (afilhadaJaTemMadrinha) {
            throw new RuntimeException("Afilhada já possui uma madrinha neste programa.");
        }

        // Se houver madrinha sugerida, validar limitações
        if (madrinhaSugestao != null) {

            long quantidadeAfilhadas = repository.countByProgramaAndMadrinhaAndStatusConexao(
                    programa, madrinhaSugestao, "ACEITA"
            );

            if (quantidadeAfilhadas >= 5) {
                throw new RuntimeException("Esta madrinha já atingiu o limite de 5 afilhadas.");
            }
        }

        // Cria e salva a solicitação
        ParticipantesPrograma solicitacao = repository.save(
                ParticipantesPrograma.builder()
                        .programa(programa)
                        .afilhada(afilhada)
                        .madrinha(madrinhaSugestao)
                        .statusConexao("PENDENTE")
                        .build()
        );

        // Notificar a madrinha
        if (madrinhaSugestao != null) {
            notificacaoService.criarNotificacao(
                    madrinhaSugestao.getIdUsuaria(),                     // destinatária
                    TipoNotificacao.SOLICITACAO_ACOLHIMENTO,             // tipo
                    "Você recebeu uma nova solicitação de acolhimento da usuária " + afilhada.getNome(),
                    solicitacao.getIdParticipacao(),                     // referenciaId (id da participação)
                    programa.getIdPrograma(),                            // programaId  (novo parâmetro)
                    afilhada.getIdUsuaria()                              // usuariaRelacionadoId (afilhada)
            );
        }

        return solicitacao;
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

        // Verifica se a madrinha já atingiu o limite de afilhadas
        long conexoesAtivas = repository.countByMadrinhaAndStatusConexao(madrinha, STATUS_ACEITO);

        if (conexoesAtivas >= LIMITE_AFILHADAS) {
            throw new RuntimeException(
                    "Regra de Negócio violada: Esta Madrinha já atingiu o limite máximo de "
                            + LIMITE_AFILHADAS + " afilhadas aceitas."
            );
        }

        // Busca a solicitação
        ParticipantesPrograma p = repository.findById(idParticipacao)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        // Atualiza os dados
        p.setMadrinha(madrinha);
        p.setStatusConexao(STATUS_ACEITO);

        // Salva somente UMA vez
        ParticipantesPrograma atualizado = repository.save(p);

        //  Notificar a afilhada
        notificacaoService.criarNotificacao(
                p.getAfilhada().getIdUsuaria(),        // destinatária
                TipoNotificacao.ACOLHIMENTO_ACEITO,   // tipo
                "Sua solicitação de acolhimento foi aceita por " + madrinha.getNome(),
                p.getIdParticipacao(),                 // referenciaId
                p.getPrograma().getIdPrograma(),       // programaId
                madrinha.getIdUsuaria()                // usuariaRelacionadoId
        );

        return atualizado;
    }

    public ParticipantesPrograma rejeitarSolicitacao(Long idParticipacao, Usuaria madrinha) {

        ParticipantesPrograma p = repository.findById(idParticipacao)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        p.setMadrinha(madrinha);
        p.setStatusConexao("REJEITADA");

        ParticipantesPrograma atualizado = repository.save(p);

        notificacaoService.criarNotificacao(
                p.getAfilhada().getIdUsuaria(),        // destinatária da notificação
                TipoNotificacao.ACOLHIMENTO_REJEITADO, // tipo
                "Sua solicitação de acolhimento foi rejeitada por " + madrinha.getNome(),
                p.getIdParticipacao(),                 // referenciaId
                p.getPrograma().getIdPrograma(),       // programaId
                madrinha.getIdUsuaria()                // usuariaRelacionadoId (madrinha)
        );

        return atualizado;
    }

    public void excluirParticipacao(Long idParticipacao) {
        ParticipantesPrograma participante = repository.findById(idParticipacao)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        repository.delete(participante);
    }
}