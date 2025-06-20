package com.bagalaxy.BGV.controller;

import com.bagalaxy.BGV.dto.MovieDTO;
import com.bagalaxy.BGV.form.movie.MovieCreateForm;
import com.bagalaxy.BGV.form.movie.MovieFilterForm;
import com.bagalaxy.BGV.form.movie.MovieUpdateForm;
import com.bagalaxy.BGV.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<Page<MovieDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(movieService.getAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MovieDTO>> search(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(movieService.searchByTitle(keyword, pageable));
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<MovieDTO>> filter(@RequestBody MovieFilterForm form, Pageable pageable) {
        return ResponseEntity.ok(movieService.filter(form, pageable));
    }

    @GetMapping("/genre")
    public ResponseEntity<Page<MovieDTO>> getMovieByGenreId(@RequestParam Long genreId, Pageable pageable) {
        return ResponseEntity.ok(movieService.getMovieByGenreId(genreId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MovieDTO> create(@RequestBody @Valid MovieCreateForm form) {
        return ResponseEntity.ok(movieService.create(form));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @RequestBody @Valid MovieUpdateForm form) {
        return ResponseEntity.ok(movieService.update(id, form));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
