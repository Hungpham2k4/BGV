package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.MovieDTO;
import com.bagalaxy.BGV.form.movie.MovieCreateForm;
import com.bagalaxy.BGV.form.movie.MovieFilterForm;
import com.bagalaxy.BGV.form.movie.MovieUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<MovieDTO> getAll(Pageable pageable);

    Page<MovieDTO> searchByTitle(String keyword, Pageable pageable);

    Page<MovieDTO> filter(MovieFilterForm form, Pageable pageable);

    MovieDTO getById(Long id);

    MovieDTO create(MovieCreateForm form);

    MovieDTO update(Long id, MovieUpdateForm form);
    Page<MovieDTO> getMovieByGenreId(Long genreId, Pageable pageable);
    void delete(Long id);
}
