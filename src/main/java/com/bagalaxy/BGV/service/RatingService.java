package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.RatingDTO;
import com.bagalaxy.BGV.form.rating.RatingCreateForm;
import com.bagalaxy.BGV.form.rating.RatingUpdateForm;

import java.util.List;

public interface RatingService {
    List<RatingDTO> getByMovieId(Long movieId);

    RatingDTO create(Long userId, RatingCreateForm form);

    RatingDTO update(Long userId, Long movieId, RatingUpdateForm form);

    void delete(Long id);

    Double getAverageScoreByMovieId(Long movieId);
}
