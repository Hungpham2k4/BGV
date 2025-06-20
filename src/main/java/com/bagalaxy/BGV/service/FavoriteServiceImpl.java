package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.FavoriteDTO;
import com.bagalaxy.BGV.entity.Favorite;
import com.bagalaxy.BGV.entity.Movie;
import com.bagalaxy.BGV.entity.User;
import com.bagalaxy.BGV.form.favorite.FavoriteForm;
import com.bagalaxy.BGV.repository.FavoriteRepository;
import com.bagalaxy.BGV.repository.MovieRepository;
import com.bagalaxy.BGV.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<FavoriteDTO> getByUserId(Long userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        return favorites.stream().map(fav -> {
            FavoriteDTO dto = new FavoriteDTO();
            dto.setUserId(fav.getUser().getId());
            dto.setMovieId(fav.getMovie().getId());
            dto.setMovieTitle(fav.getMovie().getTitle());
            return dto;
        }).toList();
    }

    @Override
    public FavoriteDTO addFavorite(Long userId, FavoriteForm form) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Movie movie = movieRepository.findById(form.getMovieId())
                .orElseThrow(() -> new NotFoundException("Movie not found"));

        if (favoriteRepository.existsByUserIdAndMovieId(userId, movie.getId())) {
            throw new IllegalStateException("Movie already favorited");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setMovie(movie);

        Favorite saved = favoriteRepository.save(favorite);

        FavoriteDTO dto = new FavoriteDTO();
        dto.setUserId(saved.getUser().getId());
        dto.setMovieId(saved.getMovie().getId());
        dto.setMovieTitle(saved.getMovie().getTitle());
        return dto;
    }

    @Override
    public void removeFavorite(Long userId, Long movieId) {
        if (!favoriteRepository.existsByUserIdAndMovieId(userId, movieId)) {
            throw new NotFoundException("Favorite not found");
        }
        favoriteRepository.deleteByUserIdAndMovieId(userId, movieId);
    }

    @Override
    public Page<FavoriteDTO> getAllByMovieId(Long movieId, Pageable pageable) {
        Page<Favorite> page = favoriteRepository.findByMovieId(movieId, pageable);
        return page.map(fav -> {
            FavoriteDTO dto = new FavoriteDTO();
            dto.setUserId(fav.getUser().getId());
            dto.setMovieId(fav.getMovie().getId());
            dto.setMovieTitle(fav.getMovie().getTitle());
            return dto;
        });
    }

}
