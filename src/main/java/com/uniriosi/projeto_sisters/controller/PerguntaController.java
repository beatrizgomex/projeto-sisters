package com.uniriosi.projeto_sisters.controller;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Pergunta;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.service.PerguntaService;
import com.uniriosi.projeto_sisters.service.UsuariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pergunta")
@RequiredArgsConstructor

public class PerguntaController {
    private record PerguntaRequest(Long idUsuaria, String titulo, String corpo) {}
    private record PerguntaEditRequest(Long idUsuaria, String titulo, String corpo) {}

    private final PerguntaService perguntaService;

    @PostMapping // Chamando o método CORRETO do Service
    public ResponseEntity<Pergunta> criarPergunta(@RequestBody PerguntaRequest dto){
        Pergunta novaPergunta = perguntaService.criarPergunta(
                dto.idUsuaria(),
                dto.titulo(),
                dto.corpo()
        );
        // O método criarPergunta já salva os dados.
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPergunta);
    }

    @GetMapping
    public ResponseEntity<Pergunta> buscarPergunta(@RequestParam Long idPergunta){
        return ResponseEntity.ok(perguntaService.buscarPergunta(idPergunta));
    }

    @DeleteMapping
    public ResponseEntity<Void> excluirPergunta(@RequestParam Long idPergunta){
        perguntaService.excluirPergunta(idPergunta);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idPergunta}") // Usa PUT, e o ID da pergunta na URL (Path Variable)
    public ResponseEntity<Void> editarPergunta(@PathVariable Long idPergunta,
                                               @RequestBody PerguntaEditRequest dto){ // Recebe dados no corpo

        // 1. Converte o DTO para uma entidade Pergunta (parcial) que o Service espera
        Pergunta dadosNovos = Pergunta.builder()
                .titulo(dto.titulo())
                .corpo(dto.corpo())
                .build();

        // 2. Chama o Service com todos os dados necessários
        perguntaService.editarPergunta(idPergunta, dadosNovos, dto.idUsuaria());

        return ResponseEntity.ok().build();
    }
}