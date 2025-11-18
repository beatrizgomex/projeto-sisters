package com.uniriosi.projeto_sisters.infrastructure.repository;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Mensagem;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    List<Mensagem> findByDestinatariaAndStatusLeituraFalse(Usuaria destinataria);

    @Query("SELECT m FROM Mensagem m WHERE " +
            "(m.remetente = :u1 AND m.destinataria = :u2) OR " +
            "(m.remetente = :u2 AND m.destinataria = :u1) " +
            "ORDER BY m.dataEnvio ASC")
    List<Mensagem> findConversationHistory(@Param("u1") Usuaria u1, @Param("u2") Usuaria u2);

}
