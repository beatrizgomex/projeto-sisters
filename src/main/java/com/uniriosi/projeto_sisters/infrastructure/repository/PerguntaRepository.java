package com.uniriosi.projeto_sisters.infrastructure.repository;


import com.uniriosi.projeto_sisters.infrastructure.entitys.Pergunta;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long>{
    List<Pergunta> findByAutora(Usuaria autora);
    List<Pergunta> findByTitulo(String titulo);
}
