package com.uniriosi.projeto_sisters.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Notificacao;
import com.uniriosi.projeto_sisters.service.NotificacaoService;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoService service;

    @GetMapping
    public List<Notificacao> listar(@RequestParam Long usuariaId) {
        return service.listarPorUsuaria(usuariaId);
    }

    @PutMapping("/{id}/marcar-lida")
    public ResponseEntity<Void> marcarComoLida(@PathVariable Long id) {
        service.marcarComoLida(id);
        return ResponseEntity.ok().build();
    }
}
