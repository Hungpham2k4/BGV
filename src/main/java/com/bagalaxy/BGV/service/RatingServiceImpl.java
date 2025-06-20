package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.RatingDTO;
import com.bagalaxy.BGV.entity.Movie;
import com.bagalaxy.BGV.entity.Rating;
import com.bagalaxy.BGV.entity.User;
import com.bagalaxy.BGV.form.rating.RatingCreateForm;
import com.bagalaxy.BGV.form.rating.RatingUpdateForm;
import com.bagalaxy.BGV.repository.MovieRepository;
import com.bagalaxy.BGV.repository.RatingRepository;
import com.bagalaxy.BGV.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<RatingDTO> getByMovieId(Long movieId) {
        List<Rating> ratings = ratingRepository.findByMovieId(movieId);
        return ratings.stream()
                .map(r -> {
                    RatingDTO dto = modelMapper.map(r, RatingDTO.class);
                    dto.setUserId(r.getUser().getId());
                    dto.setUsername(r.getUser().getUsername());
                    dto.setMovieId(r.getMovie().getId());
                    return dto;
                })
                .toList();
    }

    @Override
    public RatingDTO create(Long userId, RatingCreateForm form) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Movie movie = movieRepository.findById(form.getMovieId())
                .orElseThrow(() -> new NotFoundException("Movie not found"));

        if (ratingRepository.findByMovieIdAndUserId(movie.getId(), userId).isPresent()) {
            throw new IllegalStateException("User already rated this movie");
        }

        Rating rating = new Rating();
        rating.setScore(form.getScore());
        rating.setReview(form.getReview());
        rating.setUser(user);
        rating.setMovie(movie);

        Rating saved = ratingRepository.save(rating);
        RatingDTO dto = modelMapper.map(saved, RatingDTO.class);
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setMovieId(movie.getId());
        return dto;
    }

    @Override
    public RatingDTO update(Long userId, Long movieId, RatingUpdateForm form) {
        Rating rating = ratingRepository.findByUserIdAndMovieId(movieId, userId)
                .orElseThrow(() -> new NotFoundException("Rating not found"));

        if (form.getScore() > 0) {
            rating.setScore(form.getScore());
        }else {
            rating.setScore(1);
        }
        if (form.getReview() != null) {
            rating.setReview(form.getReview());
        }

        Rating updated = ratingRepository.save(rating);
        RatingDTO dto = modelMapper.map(updated, RatingDTO.class);
        dto.setUserId(updated.getUser().getId());
        dto.setUsername(updated.getUser().getUsername());
        dto.setMovieId(updated.getMovie().getId());
        return dto;
    }

    @Override
    public void delete(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new NotFoundException("Rating not found");
        }
        ratingRepository.deleteById(id);
    }

    @Override
    public Double getAverageScoreByMovieId(Long movieId) {
        return ratingRepository.findAverageScoreByMovieId(movieId);
    }
}
