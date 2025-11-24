package com.uniriosi.projeto_sisters.service;
import com.uniriosi.projeto_sisters.controller.dto.request.LoginRequest;
import com.uniriosi.projeto_sisters.controller.dto.request.UsuariaRequest;
import com.uniriosi.projeto_sisters.controller.dto.request.UsuariaUpdateRequest;
import com.uniriosi.projeto_sisters.controller.dto.response.UsuariaResponse;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Aluna;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.AlunaRepository;
import com.uniriosi.projeto_sisters.infrastructure.repository.UsuariaRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class UsuariaService {

    private final UsuariaRepository usuariaRepository;
    private final AlunaRepository alunaRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String UNIRIO_EMAIL = ".*@(edu\\.unirio\\.br|uniriotec\\.br|unirio\\.br)$";
    private static final String ALUNA_EMAIL = ".*@(edu\\.unirio\\.br)$";


    public UsuariaService(UsuariaRepository usuariaRepository, AlunaRepository alunaRepository, PasswordEncoder passwordEncoder) {
        this.usuariaRepository = usuariaRepository;
        this.alunaRepository = alunaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuaria cadastrar(UsuariaRequest usuaria) {

        if(!usuaria.getEmail().toLowerCase().matches(UNIRIO_EMAIL)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email nao esta no dominio UNIRIO"
            );
        }
        if(usuariaRepository.existsByEmail(usuaria.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email já cadastrado"
            );
        }
        String senhaCrip = passwordEncoder.encode(usuaria.getSenha());

        if ("Aluna".equalsIgnoreCase(usuaria.getPapel()) && !usuaria.getEmail().toLowerCase().matches(ALUNA_EMAIL)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O papel 'Aluna' exige um email @edu.unirio.br"
            );
        }

        if(usuaria.getEmail().matches(ALUNA_EMAIL)) {
            Aluna aluna = Aluna.builder()
                    .nome(usuaria.getNome())
                    .email(usuaria.getEmail())
                    .senha(senhaCrip)
                    .curso(usuaria.getCurso())
                    .semestre(usuaria.getSemestre())
                    .papel(usuaria.getPapel())
                    .papelAcolhimento(usuaria.getPapelAcolhimento())
                    .bioCurta(usuaria.getBioCurta())
                    .preferenciasPriv(usuaria.getPreferenciasPriv())
                    .interesses(usuaria.getInteresses())
                    .habilidades(usuaria.getHabilidades())
                    .build();
            return alunaRepository.save(aluna);
        } else {
            Usuaria usuariaCriada = Usuaria.builder()
                    .nome(usuaria.getNome())
                    .email(usuaria.getEmail())
                    .senha(senhaCrip)
                    .papel(usuaria.getPapel())
                    .papelAcolhimento(usuaria.getPapelAcolhimento())
                    .bioCurta(usuaria.getBioCurta())
                    .preferenciasPriv(usuaria.getPreferenciasPriv())
                    .interesses(usuaria.getInteresses())
                    .habilidades(usuaria.getHabilidades())
                    .build();
            return usuariaRepository.save(usuariaCriada);
        }
    }

    public void atualizarPerfil(Long id, UsuariaUpdateRequest usuariaNovosDados){
        Usuaria usuariaEntity = usuariaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuária não encontrada"
        ));

        if (usuariaNovosDados.getNome() != null)
            usuariaEntity.setNome(usuariaNovosDados.getNome());
        if (usuariaNovosDados.getBioCurta() != null)
            usuariaEntity.setBioCurta(usuariaNovosDados.getBioCurta());
        if (usuariaNovosDados.getPreferenciasPriv() != null)
            usuariaEntity.setPreferenciasPriv(usuariaNovosDados.getPreferenciasPriv());
        if (usuariaNovosDados.getInteresses() != null)
            usuariaEntity.setInteresses(usuariaNovosDados.getInteresses());
        if (usuariaNovosDados.getHabilidades() != null)
            usuariaEntity.setHabilidades(usuariaNovosDados.getHabilidades());
        if (usuariaNovosDados.getPapel() != null)
            usuariaEntity.setPapel(usuariaNovosDados.getPapel());
        if (usuariaNovosDados.getPapelAcolhimento() != null)
            usuariaEntity.setPapelAcolhimento(usuariaNovosDados.getPapelAcolhimento());
        if (usuariaNovosDados.getSenha() != null && !usuariaNovosDados.getSenha().isBlank()) {
            String senhaCrip = passwordEncoder.encode(usuariaNovosDados.getSenha());
            usuariaEntity.setSenha(senhaCrip);
        }
        if (usuariaEntity instanceof Aluna) {
            Aluna alunaDadosNovos = (Aluna) usuariaEntity;
            if (usuariaNovosDados.getSemestre() != null)
                alunaDadosNovos.setSemestre(usuariaNovosDados.getSemestre());
            alunaRepository.save(alunaDadosNovos);
        } else {
            usuariaRepository.save(usuariaEntity);
        }
    }


    public void excluirPerfil(Long id) {
        if (!usuariaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuária não encontrada"
            );
        }
        usuariaRepository.deleteById(id);
    }

    public List<Usuaria> buscarPerfil(String nome) {
        return usuariaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public UsuariaResponse convertToResponse(Usuaria usuaria) {
        UsuariaResponse.UsuariaResponseBuilder builder = UsuariaResponse.builder()
                .idUsuaria(usuaria.getIdUsuaria())
                .nome(usuaria.getNome())
                .email(usuaria.getEmail())
                .papel(usuaria.getPapel())
                .papelAcolhimento(usuaria.getPapelAcolhimento())
                .bioCurta(usuaria.getBioCurta())
                .preferenciasPriv(usuaria.getPreferenciasPriv())
                .interesses(usuaria.getInteresses())
                .habilidades(usuaria.getHabilidades());

        if (usuaria instanceof Aluna) {
            Aluna aluna = (Aluna) usuaria;
            builder.curso(aluna.getCurso())
                    .semestre(aluna.getSemestre());
        }

        return builder.build();
    }

    public Usuaria login(LoginRequest loginRequest) {

        Usuaria usuaria = usuariaRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Email ou senha incorretos"
                ));

        boolean senhaConfere = passwordEncoder.matches(loginRequest.getSenha(), usuaria.getSenha());

        if (!senhaConfere) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Email ou senha incorretos"
            );
        }
        return usuaria;
    }
}
