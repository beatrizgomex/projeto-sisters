package com.uniriosi.projeto_sisters.infrastructure.repository;

import com.uniriosi.projeto_sisters.infrastructure.entitys.ParticipantesPrograma;
import com.uniriosi.projeto_sisters.infrastructure.entitys.ProgramaAcolhimento;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantesProgramaRepository extends JpaRepository<ParticipantesPrograma, Long> {
    Optional<ParticipantesPrograma> findByProgramaAndUsuaria(ProgramaAcolhimento programa, Usuaria usuaria);
}
