package com.uniriosi.projeto_sisters.service;
import com.uniriosi.projeto_sisters.controller.dto.response.MensagemResponse;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Mensagem;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.MensagemRepository;
import com.uniriosi.projeto_sisters.infrastructure.repository.UsuariaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final UsuariaRepository usuariaRepository;

    public MensagemService(MensagemRepository mensagemRepository, UsuariaRepository usuariaRepository) {
        this.mensagemRepository = mensagemRepository;
        this.usuariaRepository = usuariaRepository;
    }

    public Mensagem enviarMensagem(Long idRemetente, Long idDestinatario, String conteudo){

        if (idDestinatario == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O ID da destinatária é obrigatório."
            );
        }
        if (conteudo == null || conteudo.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O conteúdo da mensagem é obrigatório."
            );
        }

        Usuaria remetente = usuariaRepository.findById(idRemetente)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Remetente não encontrada."
                ));

        Usuaria destinataria = usuariaRepository.findById(idDestinatario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Destinatária não encontrada."
                ));

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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Mensagem não encontrada."
                ));

        mensagem.setStatusLeitura(true);
        mensagemRepository.saveAndFlush(mensagem);
    }

    public List<Mensagem> buscarMensagens(Long idU1, Long idU2){
        Usuaria u1 = usuariaRepository.findById(idU1)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuária 1 não encontrada."
                ));
        Usuaria u2 = usuariaRepository.findById(idU2)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuária 2 não encontrada."
                ));
        return mensagemRepository.findConversationHistory(u1, u2);
    }

    public Mensagem buscarUltimaMensagem(Long idUsuaria) {
        return mensagemRepository.findUltimaMensagem(idUsuaria)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Nenhuma mensagem encontrada para essa usuária"
                ));
    }

    public MensagemResponse convertToResponse(Mensagem mensagem) {
        return MensagemResponse.builder()
                .idMensagem(mensagem.getIdMensagem())
                .conteudo(mensagem.getConteudo())
                .dataEnvio(mensagem.getDataEnvio())
                .remetenteId(mensagem.getRemetente().getIdUsuaria())
                .destinatariaId(mensagem.getDestinataria().getIdUsuaria())
                .build();
    }
}
