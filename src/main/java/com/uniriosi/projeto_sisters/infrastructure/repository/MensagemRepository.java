package com.uniriosi.projeto_sisters.infrastructure.repository;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Mensagem;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    List<Mensagem> findByRemetenteAndDestinataria(Usuaria remetente, Usuaria destinataria);
    List<Mensagem> findByDestinatariaAndStatusLeituraFalse(Usuaria destinataria);

}
