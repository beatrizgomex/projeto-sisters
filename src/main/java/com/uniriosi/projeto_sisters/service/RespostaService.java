package com.uniriosi.projeto_sisters.service;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Pergunta;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Resposta;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.PerguntaRepository;
import com.uniriosi.projeto_sisters.infrastructure.repository.RespostaRepository;
import com.uniriosi.projeto_sisters.infrastructure.repository.UsuariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RespostaService {
    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private UsuariaRepository usuariaRepository;


    public Resposta criarResposta(Long idUsuaria, Long idPergunta, String corpo) {
        Usuaria autora = usuariaRepository.findById(idUsuaria).orElseThrow(
                () -> new RuntimeException("Usuária não encontrada")
        );

        Pergunta pergunta = perguntaRepository.findById(idPergunta).orElseThrow(
                () -> new RuntimeException("Pergunta não encontrada")
        );

        Resposta resposta = Resposta.builder()
                .autora(autora)
                .pergunta(pergunta)
                .corpo(corpo)
                .dataPublicacao(LocalDateTime.now())
                .aceita(false)
                .build();

        return respostaRepository.save(resposta);
    }

    public Resposta buscarResposta(Long idResposta) {
        return respostaRepository.findById(idResposta).orElseThrow(
                () -> new RuntimeException("Resposta não encontrada")
        );
    }

    public void excluirResposta(Long idResposta, Long idUsuaria) {
        Resposta resposta = buscarResposta(idResposta);

        if (!resposta.getAutora().getIdUsuaria().equals(idUsuaria) && resposta.getPergunta().getAutora().getIdUsuaria().equals(idUsuaria)){
            throw new RuntimeException("Usuária não tem permissão para excluir esta resposta");
        }

        respostaRepository.delete(resposta);

    }

    public Resposta marcarRespostaAceita(Long idResposta, Long idUsuaria){
        Resposta resposta = buscarResposta(idResposta);
        Pergunta pergunta = resposta.getPergunta();

        if(!pergunta.getAutora().getIdUsuaria().equals(idUsuaria)){
            throw new RuntimeException("Somente a autora da pergunta pode marcar a resposta como aceita");
        }

        resposta.setAceita(true);
        return respostaRepository.save(resposta);
    }

}

