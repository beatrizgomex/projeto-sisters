package com.uniriosi.projeto_sisters.infrastructure.repository;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuariaRepository extends JpaRepository<Usuaria, Long> {

    Optional<Usuaria> findByEmail(String email);
    List<Usuaria> findByPapel(String papel);
    List<Usuaria> findByNomeContainingIgnoreCase(String nome);
    boolean existsByEmail(String email);
}
