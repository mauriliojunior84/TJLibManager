package com.tjlibmanager.api.repository;

import com.tjlibmanager.api.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

    @Query("SELECT DISTINCT l FROM Livro l LEFT JOIN FETCH l.autores LEFT JOIN FETCH l.assuntos")
    List<Livro> findAllWithRelations();

    @Query("SELECT l FROM Livro l LEFT JOIN FETCH l.autores LEFT JOIN FETCH l.assuntos WHERE l.codl = :id")
    Optional<Livro> findByIdWithRelations(@Param("id") Integer id);
}
