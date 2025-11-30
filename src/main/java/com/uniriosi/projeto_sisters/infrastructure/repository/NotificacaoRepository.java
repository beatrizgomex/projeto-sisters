package com.uniriosi.projeto_sisters.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Notificacao;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findByDestinatariaIdUsuariaOrderByDataCriacaoDesc(Long idUsuaria);
}
