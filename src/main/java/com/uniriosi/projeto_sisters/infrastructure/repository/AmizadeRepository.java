package com.uniriosi.projeto_sisters.infrastructure.repository;

import com.uniriosi.projeto_sisters.infrastructure.entitys.Amizade;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AmizadeRepository extends JpaRepository<Amizade, Long> {

    Optional<Amizade> findByUsuaria1AndUsuaria2OrUsuaria2AndUsuaria1(
            Usuaria usuariaA, Usuaria usuariaB,
            Usuaria usuariaC, Usuaria usuariaD
    );
    @Query("SELECT a FROM Amizade a WHERE (a.usuaria1 = :user OR a.usuaria2 = :user) AND a.status = :status")
    List<Amizade> findAllAcceptedFriends(@Param("user") Usuaria user, @Param("status") String status);

}