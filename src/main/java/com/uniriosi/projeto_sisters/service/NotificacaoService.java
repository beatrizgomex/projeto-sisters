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

    public Notificacao criarNotificacao(
            Long destinatariaId,
            TipoNotificacao tipo,
            String mensagem,
            Long referenciaId
    ) {
        Usuaria destinataria = usuariaRepository.findById(destinatariaId)
                .orElseThrow(() -> new RuntimeException("Usuária destinatária não encontrada"));

        Notificacao n = Notificacao.builder()
                .destinataria(destinataria)
                .tipo(tipo)
                .mensagem(mensagem)
                .referenciaId(referenciaId)
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