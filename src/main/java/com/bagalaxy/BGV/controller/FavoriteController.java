package com.bagalaxy.BGV.controller;

import com.bagalaxy.BGV.dto.FavoriteDTO;
import com.bagalaxy.BGV.form.favorite.FavoriteForm;
import com.bagalaxy.BGV.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<FavoriteDTO> addFavorite(@RequestParam Long userId, @RequestBody @Valid FavoriteForm form) {
        return ResponseEntity.ok(favoriteService.addFavorite(userId, form));
    }

    @DeleteMapping
    public ResponseEntity<?> removeFavorite(@RequestParam Long userId, @RequestParam Long movieId) {
        favoriteService.removeFavorite(userId, movieId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<FavoriteDTO>> getAllByMovieId(@RequestParam Long movieId, Pageable pageable) {
        return ResponseEntity.ok(favoriteService.getAllByMovieId(movieId, pageable));
    }

    @GetMapping("/my")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesOfCurrentUser(@RequestParam Long userId) {
        return ResponseEntity.ok(favoriteService.getByUserId(userId));
    }
}
