package com.bagalaxy.BGV.repository;

import com.bagalaxy.BGV.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByMovieIdAndUserId(Long movieId, Long userId);

    List<Rating> findByMovieId(Long movieId);
    Optional<Rating> findByUserIdAndMovieId(Long userId, Long movieId);
    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.movie.id = :movieId")
    Double findAverageScoreByMovieId(@Param("movieId") Long movieId);
}
