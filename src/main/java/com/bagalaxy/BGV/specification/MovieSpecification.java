package com.bagalaxy.BGV.specification;

import com.bagalaxy.BGV.entity.Genre;
import com.bagalaxy.BGV.entity.Movie;
import com.bagalaxy.BGV.form.movie.MovieFilterForm;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class MovieSpecification {

    public static Specification<Movie> build(MovieFilterForm form) {
        return Specification.where(hasKeyword(form.getKeyword()))
                .and(hasGenres(form.getGenres()))
                .and(releaseDateBetween(form.getFromDate(), form.getToDate()))
                .and(durationBetween(form.getMinDuration(), form.getMaxDuration()));
    }

    private static Specification<Movie> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return null;
            return cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
        };
    }

    private static Specification<Movie> hasGenres(List<String> genres) {
        return (root, query, cb) -> {
            if (genres == null || genres.isEmpty()) return null;
            query.distinct(true); // tránh bị duplicate kết quả khi join
            Join<Movie, Genre> genreJoin = root.join("genres", JoinType.INNER);
            return genreJoin.get("name").in(genres);
        };
    }

    private static Specification<Movie> releaseDateBetween(LocalDate from, LocalDate to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;
            if (from != null && to != null)
                return cb.between(root.get("releaseDate"), from, to);
            if (from != null)
                return cb.greaterThanOrEqualTo(root.get("releaseDate"), from);
            return cb.lessThanOrEqualTo(root.get("releaseDate"), to);
        };
    }

    private static Specification<Movie> durationBetween(Integer min, Integer max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null)
                return cb.between(root.get("duration"), min, max);
            if (min != null)
                return cb.greaterThanOrEqualTo(root.get("duration"), min);
            return cb.lessThanOrEqualTo(root.get("duration"), max);
        };
    }
}
