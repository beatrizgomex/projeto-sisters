package com.uniriosi.projeto_sisters.controller;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Resposta;
import com.uniriosi.projeto_sisters.service.RespostaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resposta") // Rota base para todas as operações de Resposta
@RequiredArgsConstructor
public class RespostaController {

    // Injeção do service de Resposta
    private final RespostaService respostaService;

    @PostMapping
    public ResponseEntity<Resposta> criarResposta(
            @RequestParam Long idUsuaria,
            @RequestParam Long idPergunta,
            @RequestBody Resposta respostaCorpo // Assumindo que o corpo (string) está dentro da Entidade/DTO
    ) {
        try {
            // O Service retorna a Resposta salva com o ID gerado
            Resposta novaResposta = respostaService.criarResposta(
                    idUsuaria,
                    idPergunta,
                    respostaCorpo.getCorpo() // Pega o conteúdo do corpo da requisição
            );
            return new ResponseEntity<>(novaResposta, HttpStatus.CREATED); // Status 201
        } catch (RuntimeException e) {
            // Se a Usuária ou Pergunta não forem encontradas
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Resposta> buscarResposta(@RequestParam Long idResposta){
        try {
            return ResponseEntity.ok(respostaService.buscarResposta(idResposta));
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND); // Status 404
        }
    }

    /**
     * DELETE: Exclui uma Resposta.
     * A validação de quem pode excluir (autora da Resposta ou autora da Pergunta) está no Service.
     * Exemplo de Rota: DELETE /resposta?idResposta=3&idUsuaria=1
     */
    @DeleteMapping
    public ResponseEntity<Void> excluirResposta(
            @RequestParam Long idResposta,
            @RequestParam Long idUsuaria // ID da usuária logada/tentando excluir
    ){
        try {
            respostaService.excluirResposta(idResposta, idUsuaria);
            return ResponseEntity.ok().build(); // Status 200 (OK)
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN); // Status 403 (Acesso negado) ou 404
        }
    }
    @PatchMapping("/aceitar")
    public ResponseEntity<Resposta> marcarRespostaAceita(
            @RequestParam Long idResposta,
            @RequestParam Long idUsuaria // ID da usuária logada
    ){
        try {
            Resposta respostaAceita = respostaService.marcarRespostaAceita(idResposta, idUsuaria);
            return ResponseEntity.ok(respostaAceita); // Status 200 e retorna a resposta atualizada
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN); // Status 403 (Acesso negado)
        }
    }
}