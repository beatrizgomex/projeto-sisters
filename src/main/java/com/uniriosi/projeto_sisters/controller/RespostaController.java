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
            @RequestBody Resposta respostaCorpo
    ) {
        try {
            Resposta novaResposta = respostaService.criarResposta(
                    idUsuaria,
                    idPergunta,
                    respostaCorpo.getCorpo()
            );
            return new ResponseEntity<>(novaResposta, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Resposta> buscarResposta(@RequestParam Long idResposta){
        try {
            return ResponseEntity.ok(respostaService.buscarResposta(idResposta));
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> excluirResposta(
            @RequestParam Long idResposta,
            @RequestParam Long idUsuaria
    ){
        try {
            respostaService.excluirResposta(idResposta, idUsuaria);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("/aceitar")
    public ResponseEntity<Resposta> marcarRespostaAceita(
            @RequestParam Long idResposta,
            @RequestParam Long idUsuaria
    ){
        try {
            Resposta respostaAceita = respostaService.marcarRespostaAceita(idResposta, idUsuaria);
            return ResponseEntity.ok(respostaAceita);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}