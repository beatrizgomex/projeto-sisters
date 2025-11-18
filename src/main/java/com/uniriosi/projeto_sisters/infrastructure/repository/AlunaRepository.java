package com.uniriosi.projeto_sisters.infrastructure.repository;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Aluna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunaRepository extends JpaRepository<Aluna, Long> {
    // Não precisa de métodos aqui, o Spring Data JPA
    // já fornece save(), findById(), findAll(), etc.
}