package com.uniriosi.projeto_sisters.controller;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Amizade;
import com.uniriosi.projeto_sisters.service.AmizadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/amizades")
@CrossOrigin(origins = "http://localhost:4200")
public class AmizadeController {

    private final AmizadeService amizadeService;

    public AmizadeController(AmizadeService amizadeService) {
        this.amizadeService = amizadeService;
    }


    @PostMapping("/solicitar")
    public ResponseEntity<Amizade> solicitarAmizade(
            @RequestParam Long solicitanteId,
            @RequestParam Long solicitadaId) {

        Amizade novaAmizade = amizadeService.solicitarAmizade(solicitanteId, solicitadaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAmizade);
    }


    @PutMapping("/{id}/aceitar")
    public ResponseEntity<Amizade> aceitarAmizade(
            @PathVariable Long id,
            @RequestParam Long usuariaId) {

        Amizade amizadeAceita = amizadeService.aceitarAmizade(id, usuariaId);
        return ResponseEntity.ok(amizadeAceita);
    }

    @PutMapping("/{id}/recusar")
    public ResponseEntity<Amizade> recusarAmizade(
            @PathVariable Long id,
            @RequestParam Long usuariaId) {

        Amizade amizadeRecusada = amizadeService.recusarAmizade(id, usuariaId);
        return ResponseEntity.ok(amizadeRecusada);
    }


    @GetMapping("/status")
    public ResponseEntity<Boolean> verificarStatus(
            @RequestParam Long u1,
            @RequestParam Long u2) {

        boolean saoAmigas = amizadeService.saoAmigas(u1, u2);
        return ResponseEntity.ok(saoAmigas);
    }
}
