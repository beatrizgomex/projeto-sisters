package com.uniriosi.projeto_sisters.controller;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Noticia;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.UsuariaRepository; // <-- NOVO IMPORT
import com.uniriosi.projeto_sisters.service.NoticiaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/noticias")
public class NoticiaController {

    private final NoticiaService noticiaService;
    private final UsuariaRepository usuariaRepository;

    public NoticiaController(NoticiaService noticiaService, UsuariaRepository usuariaRepository) {
        this.noticiaService = noticiaService;
        this.usuariaRepository = usuariaRepository;
    }


    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Noticia> aprovarNoticia(@PathVariable Long id, @RequestParam Long usuarioLogadoId) {

        Usuaria usuaria = usuariaRepository.findById(usuarioLogadoId)
                .orElseThrow(() -> new RuntimeException("Usuária não encontrada ou não logada"));

        if (!usuaria.getPapel().equalsIgnoreCase("administradora")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Noticia noticiaAprovada = noticiaService.aprovarNoticia(id);
        return ResponseEntity.ok(noticiaAprovada);
    }

    @PostMapping
    public ResponseEntity<Noticia> criarNoticia(@RequestBody Noticia noticia, @RequestParam Long autoraId) {
        Noticia novaNoticia = noticiaService.criarNoticia(noticia, autoraId);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaNoticia);
    }

    @GetMapping
    public ResponseEntity<List<Noticia>> listarNoticias() {
        List<Noticia> noticias = noticiaService.listarTodasNoticiasAprovadas();
        return ResponseEntity.ok(noticias);
    }

    @GetMapping("/status")
    public ResponseEntity<List<Noticia>> buscarPorStatus(@RequestParam String status) {
        return ResponseEntity.ok(noticiaService.buscarNoticiasPorStatus(status));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirNoticia(@PathVariable Long id, @RequestParam Long usuarioLogadoId) {

        Usuaria usuaria = usuariaRepository.findById(usuarioLogadoId)
                .orElseThrow(() -> new RuntimeException("Usuária não encontrada ou não logada"));

        if (!usuaria.getPapel().equalsIgnoreCase("administradora")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        noticiaService.excluirNoticia(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<Noticia> rejeitarNoticia(@PathVariable Long id, @RequestParam Long usuarioLogadoId) {

        Usuaria usuaria = usuariaRepository.findById(usuarioLogadoId)
                .orElseThrow(() -> new RuntimeException("Usuária não encontrada ou não logada"));

        if (!usuaria.getPapel().equalsIgnoreCase("administradora")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Noticia noticiaRejeitada = noticiaService.rejeitarNoticia(id);

        return ResponseEntity.ok(noticiaRejeitada);
    }
}