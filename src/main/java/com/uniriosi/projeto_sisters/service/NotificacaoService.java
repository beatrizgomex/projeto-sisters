package com.uniriosi.projeto_sisters.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Notificacao;
import com.uniriosi.projeto_sisters.infrastructure.entitys.TipoNotificacao;
import com.uniriosi.projeto_sisters.infrastructure.repository.NotificacaoRepository;
import com.uniriosi.projeto_sisters.infrastructure.repository.UsuariaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {


    private final NotificacaoRepository notificacaoRepository;
    private final UsuariaRepository usuariaRepository;

    /**
     * Cria uma nova notificação completa.
     *
     * @param destinatariaId         ID da usuária que receberá a notificação
     * @param tipo                   TipoNotificacao
     * @param mensagem               Mensagem exibida
     * @param referenciaId           ID da participação (ParticipantesPrograma)
     * @param programaId             ID do programa de acolhimento
     * @param usuariaRelacionadoId   ID da usuária relacionada (facultativo)
     */
    public Notificacao criarNotificacao(
            Long destinatariaId,
            TipoNotificacao tipo,
            String mensagem,
            Long referenciaId,
            Long programaId,
            Long usuariaRelacionadoId
    ) {
        Usuaria destinataria = usuariaRepository.findById(destinatariaId)
                .orElseThrow(() -> new RuntimeException("Usuária destinatária não encontrada"));

        Notificacao n = Notificacao.builder()
                .destinataria(destinataria)
                .tipo(tipo)
                .mensagem(mensagem)
                .referenciaId(referenciaId)     // idParticipacao
                .programaId(programaId)         // idPrograma
                .usuariaRelacionadoId(usuariaRelacionadoId)
                .lida(false)
                .build();

        return notificacaoRepository.save(n);
    }

    public List<Notificacao> listarPorUsuaria(Long usuariaId) {
        return notificacaoRepository.findByDestinatariaIdUsuariaOrderByDataCriacaoDesc(usuariaId);
    }

    public void marcarComoLida(Long id) {
        Notificacao n = notificacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
        n.setLida(true);
        notificacaoRepository.save(n);
    }
}