package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.WatchHistoryDTO;
import com.bagalaxy.BGV.entity.Movie;
import com.bagalaxy.BGV.entity.User;
import com.bagalaxy.BGV.entity.WatchHistory;
import com.bagalaxy.BGV.form.watchhistory.WatchHistoryForm;
import com.bagalaxy.BGV.repository.MovieRepository;
import com.bagalaxy.BGV.repository.UserRepository;
import com.bagalaxy.BGV.repository.WatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WatchHistoryServiceImpl implements WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<WatchHistoryDTO> getByUserId(Long userId) {
        List<WatchHistory> histories = watchHistoryRepository.findByUserId(userId);
        return histories.stream()
                .map(wh -> {
                    WatchHistoryDTO dto = new WatchHistoryDTO();
                    dto.setId(wh.getId());
                    dto.setMovieId(wh.getMovie().getId());
                    dto.setMovieTitle(wh.getMovie().getTitle());
                    dto.setLastPosition(wh.getLastPosition());
                    dto.setWatchedAt(wh.getWatchedAt());
                    return dto;
                })
                .toList();
    }

    @Override
    public WatchHistoryDTO addOrUpdate(Long userId, WatchHistoryForm form) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Movie movie = movieRepository.findById(form.getMovieId())
                .orElseThrow(() -> new NotFoundException("Movie not found"));

        Optional<WatchHistory> optional = watchHistoryRepository.findByUserIdAndMovieId(userId, movie.getId());
        WatchHistory watchHistory;

        if (optional.isPresent()) {
            watchHistory = optional.get();
            watchHistory.setLastPosition(form.getLastPosition());
            watchHistory.setWatchedAt(LocalDateTime.now());
        } else {
            watchHistory = new WatchHistory();
            watchHistory.setUser(user);
            watchHistory.setMovie(movie);
            watchHistory.setLastPosition(form.getLastPosition());
            watchHistory.setWatchedAt(LocalDateTime.now());
        }

        WatchHistory saved = watchHistoryRepository.save(watchHistory);
        WatchHistoryDTO dto = new WatchHistoryDTO();
        dto.setId(saved.getId());
        dto.setMovieId(saved.getMovie().getId());
        dto.setMovieTitle(saved.getMovie().getTitle());
        dto.setLastPosition(saved.getLastPosition());
        dto.setWatchedAt(saved.getWatchedAt());
        return dto;
    }

    @Override
    public void delete(Long id) {
        if (!watchHistoryRepository.existsById(id)) {
            throw new NotFoundException("Watch history not found");
        }
        watchHistoryRepository.deleteById(id);
    }
}
