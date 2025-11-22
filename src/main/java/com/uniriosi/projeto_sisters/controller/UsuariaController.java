package com.uniriosi.projeto_sisters.controller;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.service.UsuariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarias")
public class UsuariaController {

    @Autowired
    private UsuariaService usuariaService;

    // Cadastrar uma nova usuária
    @PostMapping
    public Usuaria cadastrarUsuaria(@RequestBody Usuaria usuaria) {
        return usuariaService.cadastrar(usuaria);
    }

    // Atualizar o perfil de uma usuária
    @PutMapping("/{id}")
    public void atualizarPerfil(@PathVariable Long id, @RequestBody Usuaria usuariaDadosNovos) {
        usuariaService.atualizarPerfil(id, usuariaDadosNovos);
    }

    // Excluir uma usuária
    @DeleteMapping("/{id}")
    public void excluirPerfil(@PathVariable Long id) {
        usuariaService.excluirPerfil(id);
    }

    // 🔍 Buscar usuárias pelo nome (parcial ou completo)
    @GetMapping("/buscar")
    public List<Usuaria> buscarPerfil(@RequestParam String nome) {
        return usuariaService.buscarPerfil(nome);
    }

    // Enviar mensagem entre usuárias
    @PostMapping("/{idRemetente}/mensagem/{idDestinataria}")
    public void enviarMensagem(
            @PathVariable Long idRemetente,
            @PathVariable Long idDestinataria,
            @RequestParam String conteudo) {
        usuariaService.enviarMensagem(idRemetente, idDestinataria, conteudo);
    }
}

