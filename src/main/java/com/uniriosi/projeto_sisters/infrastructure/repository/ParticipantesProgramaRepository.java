package com.uniriosi.projeto_sisters.infrastructure.repository;

import com.uniriosi.projeto_sisters.infrastructure.entitys.ParticipantesPrograma;
import com.uniriosi.projeto_sisters.infrastructure.entitys.ProgramaAcolhimento;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantesProgramaRepository
        extends JpaRepository<ParticipantesPrograma, Long> {

    Optional<ParticipantesPrograma> findByProgramaAndAfilhada(
            ProgramaAcolhimento programa,
            Usuaria afilhada
    );

    List<ParticipantesPrograma> findByPrograma(ProgramaAcolhimento programa);

    @Query("SELECT COUNT(p) FROM ParticipantesPrograma p WHERE p.madrinha = ?1 AND p.statusConexao = 'ACEITA'")
    Long countAcceptedMenteesByMentor(Usuaria madrinha);

    long countByMadrinhaAndStatusConexao(Usuaria madrinha, String statusConexao);

    Optional<ParticipantesPrograma> findByAfilhadaAndStatusConexao(Usuaria afilhada, String statusConexao);

    default Optional<ParticipantesPrograma> findAcceptedConnectionByMentee(Usuaria afilhada) {
        return findByAfilhadaAndStatusConexao(afilhada, "ACEITA");
    }
    boolean existsByProgramaAndAfilhadaAndStatusConexao(
            ProgramaAcolhimento programa,
            Usuaria afilhada,
            String statusConexao
    );

    long countByProgramaAndMadrinhaAndStatusConexao(
            ProgramaAcolhimento programa,
            Usuaria madrinha,
            String statusConexao
    );
}
