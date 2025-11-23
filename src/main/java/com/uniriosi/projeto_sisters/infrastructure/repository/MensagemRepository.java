package com.uniriosi.projeto_sisters.infrastructure.repository;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Mensagem;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    List<Mensagem> findByRemetenteAndDestinataria(Usuaria remetente, Usuaria destinataria);
    List<Mensagem> findByDestinatariaAndStatusLeituraFalse(Usuaria destinataria);

=======
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    List<Mensagem> findByDestinatariaAndStatusLeituraFalse(Usuaria destinataria);

    @Query("SELECT m FROM Mensagem m WHERE " +
            "(m.remetente = :u1 AND m.destinataria = :u2) OR " +
            "(m.remetente = :u2 AND m.destinataria = :u1) " +
            "ORDER BY m.dataEnvio ASC")
    List<Mensagem> findConversationHistory(@Param("u1") Usuaria u1, @Param("u2") Usuaria u2);

    @Query("SELECT m FROM Mensagem m WHERE " +
            "m.remetente.idUsuaria = :id OR " +
            "m.destinataria.idUsuaria = :id " +
            "ORDER BY m.dataEnvio DESC")
    Optional<Mensagem> findUltimaMensagem(Long id);
>>>>>>> 699058a7dc2d610161cba6278577c7e5529cdca1
}
