package com.uniriosi.projeto_sisters.controller;

import com.uniriosi.projeto_sisters.controller.dto.request.MensagemRequest;
import com.uniriosi.projeto_sisters.controller.dto.response.MensagemResponse;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Mensagem;
import com.uniriosi.projeto_sisters.service.MensagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
@CrossOrigin(origins = "*") // ou "http://localhost:4200"
public class MensagemController {

    private final MensagemService mensagemService;

    public MensagemController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    @PostMapping
    public ResponseEntity<Mensagem> enviarMensagem(@RequestBody MensagemRequest request) {
        Mensagem novaMensagem = mensagemService.enviarMensagem(
                request.getIdRemetente(),
                request.getIdDestinataria(),
                request.getConteudo()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(novaMensagem);
    }

    @GetMapping("/historico/{idUsuaria1}/{idUsuaria2}")
    public ResponseEntity<List<Mensagem>> buscarHistorico(
            @PathVariable Long idUsuaria1,
            @PathVariable Long idUsuaria2
    ) {
        List<Mensagem> historico = mensagemService.buscarMensagens(idUsuaria1, idUsuaria2);

        if (historico.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historico);
    }

    @PatchMapping("/{idMensagem}/lida")
    public ResponseEntity<Void> marcarComoLida(@PathVariable Long idMensagem) {
        mensagemService.marcarStatus(idMensagem);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ultima")
    public ResponseEntity<MensagemResponse> buscarUltimaMensagem(
            @RequestParam Long u1,
            @RequestParam Long u2) {
        Mensagem mensagem = mensagemService.buscarUltimaMensagem(u1, u2);

        if (mensagem == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mensagemService.convertToResponse(mensagem));
    }

}