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
import java.util.List;

@Service
public class PerguntaService {
    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private UsuariaRepository usuariaRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    public Pergunta criarPergunta(Long idUsuaria, String titulo, String corpo){
        Usuaria autora =  usuariaRepository.findById(idUsuaria).orElseThrow(
            () -> new RuntimeException("Usuaria não encontrada")
        );

        Pergunta pergunta = Pergunta.builder()
                .autora(autora)
                .titulo(titulo)
                .corpo(corpo)
                .dataPublicacao(LocalDateTime.now())
                .build();

        return perguntaRepository.saveAndFlush(pergunta);
    }

    public void salvarPergunta(Pergunta pergunta){
        perguntaRepository.saveAndFlush(pergunta);
    }


    public Pergunta buscarPergunta(Long idPergunta){
        return  perguntaRepository.findById(idPergunta).orElseThrow(
                () -> new RuntimeException("Pergunta não encontrada")
        );
    }

    public void editarPergunta(Long idPergunta, Pergunta novaPergunta, Long idUsuaria) {
        Pergunta perguntaSelecionada = buscarPergunta(idPergunta);

        if (!perguntaSelecionada.getAutora().getIdUsuaria().equals(idUsuaria)) {
            throw new RuntimeException("Usuária não tem permissão para editar esta pergunta");
        }
        if (novaPergunta.getTitulo() != null) {
            perguntaSelecionada.setTitulo(novaPergunta.getTitulo());
        }

        if (novaPergunta.getCorpo() != null) {
            perguntaSelecionada.setCorpo(novaPergunta.getCorpo());
        }

        perguntaRepository.saveAndFlush(perguntaSelecionada);
    }

    public void excluirPergunta(Long idPergunta){
        if (!perguntaRepository.existsById(idPergunta)){
            throw new RuntimeException("Pergunta não encontrada");
        }
        perguntaRepository.deleteById(idPergunta);
    }

    public List<Resposta> buscarRespostas(Long idPergunta){
        Pergunta pergunta = buscarPergunta(idPergunta);

        return respostaRepository.findByPergunta(pergunta);
    }

}
