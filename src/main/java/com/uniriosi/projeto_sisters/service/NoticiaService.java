package com.uniriosi.projeto_sisters.service;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Noticia;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.NoticiaRepository;
import com.uniriosi.projeto_sisters.infrastructure.repository.UsuariaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticiaService {
    private final NoticiaRepository noticiaRepository;
    private final UsuariaRepository usuariaRepository;

    public NoticiaService(NoticiaRepository noticiaRepository, UsuariaRepository usuariaRepository) {
        this.noticiaRepository = noticiaRepository;
        this.usuariaRepository = usuariaRepository;
    }

    public Noticia criarNoticia(Noticia noticia, Long idAutora) {
        Usuaria autora = usuariaRepository.findById(idAutora)
                .orElseThrow(() -> new RuntimeException("Usuária não encontrada"));

        noticia.setAutora(autora);
        noticia.setDataPublicacao(LocalDateTime.now());
        noticia.setStatus("Pendente");

        return noticiaRepository.save(noticia);
    }

    public Noticia aprovarNoticia(Long idNoticia) {
        Noticia noticia = noticiaRepository.findById(idNoticia)
                .orElseThrow(() -> new RuntimeException("Notícia não encontrada"));

        noticia.setStatus("Aceito");
        return noticiaRepository.save(noticia);
    }

    public Noticia rejeitarNoticia(Long idNoticia) {
        Noticia noticia = noticiaRepository.findById(idNoticia)
                .orElseThrow(() -> new RuntimeException("Notícia não encontrada"));

        noticia.setStatus("Recusado");
        return noticiaRepository.save(noticia);
    }


    public void excluirNoticia(Long idNoticia) {
        if (!noticiaRepository.existsById(idNoticia)) {
            throw new RuntimeException("Notícia não encontrada para exclusão");
        }
        noticiaRepository.deleteById(idNoticia);
    }

    public List<Noticia> buscarNoticiasPorStatus(String status) {
        return noticiaRepository.findByStatus(status);
    }


    public List<Noticia> listarTodasNoticiasAprovadas() {
        return noticiaRepository.findByStatus("Aceito");

    }
}