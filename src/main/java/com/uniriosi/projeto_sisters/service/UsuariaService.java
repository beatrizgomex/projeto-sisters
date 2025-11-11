package com.uniriosi.projeto_sisters.service;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.UsuariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariaService {

    @Autowired
    private UsuariaRepository usuariaRepository;

    @Autowired
    private MensagemService mensagemService;

    public Usuaria cadastrar(Usuaria usuaria) {
        if(usuariaRepository.existsByEmail(usuaria.getEmail())) {
            throw new RuntimeException("Email ja existente");
        }
        return usuariaRepository.save(usuaria);
    }

    public void atualizarPerfil(Long id, Usuaria usuariaDadosNovos){
        Usuaria usuariaEntity = usuariaRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        Usuaria usuariaAtualizada = Usuaria.builder()
                .idUsuaria(usuariaEntity.getIdUsuaria())
                .nome(usuariaDadosNovos.getNome() != null ? usuariaDadosNovos.getNome() : usuariaEntity.getNome())
                .email(usuariaDadosNovos.getEmail() != null ? usuariaDadosNovos.getEmail() : usuariaEntity.getEmail())
                .senha(usuariaDadosNovos.getSenha() != null ? usuariaDadosNovos.getSenha() : usuariaEntity.getSenha())
                .papel(usuariaDadosNovos.getPapel() != null ? usuariaDadosNovos.getPapel() : usuariaEntity.getPapel())
                .papelAcolhimento(usuariaDadosNovos.getPapelAcolhimento() != null ? usuariaDadosNovos.getPapelAcolhimento() : usuariaEntity.getPapelAcolhimento())
                .bioCurta(usuariaDadosNovos.getBioCurta() != null ? usuariaDadosNovos.getBioCurta() : usuariaEntity.getBioCurta())
                .interesses(usuariaDadosNovos.getInteresses() != null ? usuariaDadosNovos.getInteresses() : usuariaEntity.getInteresses())
                .habilidades(usuariaDadosNovos.getHabilidades() != null ? usuariaDadosNovos.getHabilidades() : usuariaEntity.getHabilidades())
                .preferenciasPriv(usuariaDadosNovos.getPreferenciasPriv() != null ? usuariaDadosNovos.getPreferenciasPriv() : usuariaEntity.getPreferenciasPriv())
                .build();

        usuariaRepository.saveAndFlush(usuariaAtualizada);

    }

    public void excluirPerfil(Long id) {
        if (!usuariaRepository.existsById(id)) {
            throw new RuntimeException("Usuária não encontrada");
        }
        usuariaRepository.deleteById(id);
    }

    public List<Usuaria> buscarPerfil(String nome) {
        return usuariaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public void enviarMensagem(Long idRemetente, Long idDestinataria, String conteudo) {
        //verficar se sao amigas por amzdService
        mensagemService.enviarMensagem(idRemetente, idDestinataria, conteudo);
    }


}
