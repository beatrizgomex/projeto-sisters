package com.uniriosi.projeto_sisters.service;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Mensagem;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.MensagemRepository;
import com.uniriosi.projeto_sisters.infrastructure.repository.UsuariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UsuariaRepository usuariaRepository;

    public Mensagem enviarMensagem(Long idRemetente, Long idDestinatario, String conteudo){
        Usuaria remetente = usuariaRepository.findById(idRemetente)
                .orElseThrow(RuntimeException::new);
        Usuaria destinataria = usuariaRepository.findById(idDestinatario)
                .orElseThrow(RuntimeException::new);

        Mensagem mensagem = Mensagem.builder()
                .remetente(remetente)
                .destinataria(destinataria)
                .conteudo(conteudo)
                .dataEnvio(LocalDateTime.now())
                .statusLeitura(false)
                .build();

        return mensagemRepository.saveAndFlush(mensagem);
    }

    public void marcarStatus(Long idMensagem){
        Mensagem mensagem = mensagemRepository.findById(idMensagem)
                .orElseThrow(RuntimeException::new);
        mensagem.setStatusLeitura(true);
        mensagemRepository.saveAndFlush(mensagem);
    }
}
