package com.bagalaxy.BGV.repository;

import com.bagalaxy.BGV.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(String name);
    List<Genre> findByNameIn(List<String> names);
    List<Genre> findByIdIn(List<Long> ids);
    boolean existsByName(String name);
}
