package com.uniriosi.projeto_sisters.infrastructure.repository;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Pergunta;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Resposta;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RespostaRepository extends JpaRepository<Resposta, Long>{
    List<Resposta> findByPergunta(Pergunta pergunta);

    List<Resposta> findByAutora(Usuaria autora);


}
