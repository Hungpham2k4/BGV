package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.FavoriteDTO;
import com.bagalaxy.BGV.form.favorite.FavoriteForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FavoriteService {
    List<FavoriteDTO> getByUserId(Long userId);
    Page<FavoriteDTO> getAllByMovieId(Long movieId, Pageable pageable);
    FavoriteDTO addFavorite(Long userId, FavoriteForm form);

    void removeFavorite(Long userId, Long movieId);
}
