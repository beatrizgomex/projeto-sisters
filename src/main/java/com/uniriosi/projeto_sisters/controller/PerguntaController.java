package com.uniriosi.projeto_sisters.controller;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Pergunta;
import com.uniriosi.projeto_sisters.service.PerguntaService;
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

    // Criar uma pergunta
    @PostMapping
    public ResponseEntity<Pergunta> criarPergunta(@RequestBody PerguntaRequest dto){
        Pergunta novaPergunta = perguntaService.criarPergunta(
                dto.idUsuaria(),
                dto.titulo(),
                dto.corpo()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPergunta);
    }

    // Buscar pergunta
    @GetMapping
    public ResponseEntity<Pergunta> buscarPergunta(@RequestParam Long idPergunta){
        return ResponseEntity.ok(perguntaService.buscarPergunta(idPergunta));
    }

    // Excluir Pergunta
    @DeleteMapping
    public ResponseEntity<Void> excluirPergunta(@RequestParam Long idPergunta){
        perguntaService.excluirPergunta(idPergunta);
        return ResponseEntity.ok().build();
    }

    // Editar Pergunta
    @PutMapping("/{idPergunta}")
    public ResponseEntity<Void> editarPergunta(@PathVariable Long idPergunta,
                                               @RequestBody PerguntaEditRequest dto){
        Pergunta dadosNovos = Pergunta.builder()
                .titulo(dto.titulo())
                .corpo(dto.corpo())
                .build();

        perguntaService.editarPergunta(idPergunta, dadosNovos, dto.idUsuaria());

        return ResponseEntity.ok().build();
    }
}