package com.bagalaxy.BGV.controller;

import com.bagalaxy.BGV.dto.GenreDTO;
import com.bagalaxy.BGV.form.genre.GenreCreateForm;
import com.bagalaxy.BGV.form.genre.GenreUpdateForm;
import com.bagalaxy.BGV.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAll() {
        return ResponseEntity.ok(genreService.getAll());
    }

    @PostMapping
    public ResponseEntity<GenreDTO> create(@RequestBody @Valid GenreCreateForm form) {
        return ResponseEntity.ok(genreService.create(form));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> update(@PathVariable Long id, @RequestBody @Valid GenreUpdateForm form) {
        return ResponseEntity.ok(genreService.update(id, form));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}