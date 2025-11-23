package com.uniriosi.projeto_sisters.infrastructure.repository;
import com.uniriosi.projeto_sisters.infrastructure.entitys.MaterialAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialAcademicoRepository extends JpaRepository<MaterialAcademico, Long> {


    List<MaterialAcademico> findByStatus(String status);

    List<MaterialAcademico> findByTipo(String tipo);

    List<MaterialAcademico> findByTitulo(String titulo);
}