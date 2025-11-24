package com.uniriosi.projeto_sisters.controller;

import com.uniriosi.projeto_sisters.controller.dto.response.UsuariaResponse;
import com.uniriosi.projeto_sisters.controller.dto.request.UsuariaRequest;
import com.uniriosi.projeto_sisters.controller.dto.request.UsuariaUpdateRequest;
import com.uniriosi.projeto_sisters.controller.dto.request.LoginRequest;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.UsuariaRepository;
import com.uniriosi.projeto_sisters.service.UsuariaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarias")
@CrossOrigin(origins = "*") // ou "http://localhost:4200"
public class UsuariaController {

    private final UsuariaService usuariaService;
    private final UsuariaRepository usuariaRepository;

    public UsuariaController(UsuariaService usuariaService, UsuariaRepository usuariaRepository) {
        this.usuariaService = usuariaService;
        this.usuariaRepository = usuariaRepository;
    }

    @PostMapping
    public ResponseEntity<UsuariaResponse> cadastrar(@RequestBody UsuariaRequest usuariaRequest) {
        Usuaria novaUsuaria = usuariaService.cadastrar(usuariaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuariaService.convertToResponse(novaUsuaria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarPerfil(@PathVariable Long id, @RequestBody UsuariaUpdateRequest usuariaDadosNovos) {
        usuariaService.atualizarPerfil(id, usuariaDadosNovos);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(("/{id}"))
    public ResponseEntity<Void> excluirPerfil(@PathVariable Long id) {
        usuariaService.excluirPerfil(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Usuaria>> buscarPerfil(@RequestParam String nome ) {
        List<Usuaria> usuariaEncontradas = usuariaService.buscarPerfil(nome);

        if (usuariaEncontradas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<UsuariaResponse> responses = usuariaEncontradas.stream()
                .map(usuariaService::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuariaEncontradas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuariaResponse> buscarPorId(@PathVariable Long id) {
        Usuaria u = usuariaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuária não encontrada"));
        return ResponseEntity.ok(usuariaService.convertToResponse(u));
    }

    @GetMapping("/todas")
    public ResponseEntity<List<UsuariaResponse>> buscarTodas() {
        List<Usuaria> usuarias = usuariaRepository.findAll();

        List<UsuariaResponse> responses = usuarias.stream()
                .map(usuariaService::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuariaResponse> login(@RequestBody LoginRequest loginRequest) {
        Usuaria usuaria = usuariaService.login(loginRequest);
        return ResponseEntity.ok(
                usuariaService.convertToResponse(usuaria)
        );
    }

}
