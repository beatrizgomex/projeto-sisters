package com.uniriosi.projeto_sisters.infrastructure.repository;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Noticia;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {


    List<Noticia> findByStatus(String status);

    List<Noticia> findByAutora(Usuaria autora);
}