package com.bagalaxy.BGV.controller;

import com.bagalaxy.BGV.dto.RatingDTO;
import com.bagalaxy.BGV.form.rating.RatingCreateForm;
import com.bagalaxy.BGV.form.rating.RatingUpdateForm;
import com.bagalaxy.BGV.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingDTO> create(@RequestParam Long userId, @RequestBody @Valid RatingCreateForm form) {
        return ResponseEntity.ok(ratingService.create(userId, form));
    }

    @PutMapping("/{userId}/{movieId}")
    public ResponseEntity<RatingDTO> update(@PathVariable Long userId, @PathVariable Long movieId, @RequestBody @Valid RatingUpdateForm form) {
        return ResponseEntity.ok(ratingService.update(userId,movieId, form));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ratingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<RatingDTO>> getByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(ratingService.getByMovieId(movieId));
    }
}