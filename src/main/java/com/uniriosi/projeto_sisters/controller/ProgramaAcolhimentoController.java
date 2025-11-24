package com.uniriosi.projeto_sisters.controller;

import com.uniriosi.projeto_sisters.dto.ParticipacaoResponse;
import com.uniriosi.projeto_sisters.infrastructure.entitys.ParticipantesPrograma;
import com.uniriosi.projeto_sisters.infrastructure.entitys.ProgramaAcolhimento;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.service.ParticipantesProgramaService;
import com.uniriosi.projeto_sisters.service.ProgramaAcolhimentoService;
import com.uniriosi.projeto_sisters.service.UsuariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programas")
@CrossOrigin("*")
public class ProgramaAcolhimentoController {

    @Autowired
    private ProgramaAcolhimentoService programaService;

    @Autowired
    private ParticipantesProgramaService participantesService;

    @Autowired
    private UsuariaService usuariaService;


    @PostMapping
    public ProgramaAcolhimento criarPrograma(@RequestBody ProgramaAcolhimento programa) {
        return programaService.salvarPrograma(programa);
    }

    @GetMapping
    public List<ProgramaAcolhimento> listarProgramas() {
        return programaService.listarProgramas();
    }

    @PostMapping("/{idPrograma}/solicitacoes")
    public ParticipantesPrograma solicitarAcolhimento(
            @PathVariable Long idPrograma,
            @RequestParam Long afilhadaId,
            @RequestParam(required = false) Long madrinhaSugestaoId
    ) {
        ProgramaAcolhimento programa = programaService.buscarPorId(idPrograma);
        Usuaria afilhada = usuariaService.buscarPorId(afilhadaId);

        Usuaria madrinha = null;
        if (madrinhaSugestaoId != null) {
            madrinha = usuariaService.buscarPorId(madrinhaSugestaoId);
        }

        return participantesService.solicitarAcolhimento(
                programa,
                afilhada,
                madrinha
        );
    }

    @GetMapping("/{idPrograma}/participantes")
    public List<ParticipantesPrograma> listarParticipantes(@PathVariable Long idPrograma) {
        return programaService.listarParticipantes(idPrograma);
    }

    @PostMapping("/{idPrograma}/participacoes/{idParticipacao}/aceitar")
    public ParticipacaoResponse aceitarSolicitacao(
            @PathVariable Long idPrograma,
            @PathVariable Long idParticipacao,
            @RequestParam Long madrinhaId
    ) {
        Usuaria madrinha = usuariaService.buscarPorId(madrinhaId);
        ParticipantesPrograma p = participantesService.aceitarSolicitacao(idParticipacao, madrinha);

        return new ParticipacaoResponse(
                p.getIdParticipacao(),
                p.getPrograma().getIdPrograma(),
                p.getAfilhada().getIdUsuaria(),
                p.getMadrinha() != null ? p.getMadrinha().getIdUsuaria() : null,
                p.getStatusConexao()
        );
    }

    @PostMapping("/{idPrograma}/participacoes/{idParticipacao}/rejeitar")
    public ParticipacaoResponse rejeitarSolicitacao(
            @PathVariable Long idPrograma,
            @PathVariable Long idParticipacao,
            @RequestParam Long madrinhaId
    ) {
        Usuaria madrinha = usuariaService.buscarPorId(madrinhaId);
        ParticipantesPrograma p = participantesService.rejeitarSolicitacao(idParticipacao, madrinha);

        return new ParticipacaoResponse(
                p.getIdParticipacao(),
                p.getPrograma().getIdPrograma(),
                p.getAfilhada().getIdUsuaria(),
                p.getMadrinha() != null ? p.getMadrinha().getIdUsuaria() : null,
                p.getStatusConexao()
        );
    }

    @GetMapping("/{idPrograma}/sugerir-madrinhas")
    public List<Usuaria> sugerirMadrinhas(
            @PathVariable Long idPrograma,
            @RequestParam Long afilhadaId
    ) {
        return programaService.sugerirMadrinhasPara(idPrograma, afilhadaId);
    }
}
