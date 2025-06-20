package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.MovieDTO;
import com.bagalaxy.BGV.entity.Genre;
import com.bagalaxy.BGV.entity.Movie;
import com.bagalaxy.BGV.form.movie.MovieCreateForm;
import com.bagalaxy.BGV.form.movie.MovieFilterForm;
import com.bagalaxy.BGV.form.movie.MovieUpdateForm;
import com.bagalaxy.BGV.repository.GenreRepository;
import com.bagalaxy.BGV.repository.MovieRepository;
import com.bagalaxy.BGV.specification.MovieSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<MovieDTO> getAll(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(movie -> {
                    MovieDTO dto = modelMapper.map(movie, MovieDTO.class);
                    dto.setGenres(
                            movie.getGenres()
                                    .stream()
                                    .map(Genre::getName)
                                    .toList()
                    );
                    return dto;
                });
    }

    @Override
    public Page<MovieDTO> searchByTitle(String keyword, Pageable pageable) {
        return movieRepository.findByTitleContainingIgnoreCase(keyword, pageable)
                .map(movie -> {
                    MovieDTO dto = modelMapper.map(movie, MovieDTO.class);
                    dto.setGenres(
                            movie.getGenres()
                                    .stream()
                                    .map(Genre::getName)
                                    .toList()
                    );
                    return dto;
                });
    }

    @Override
    public Page<MovieDTO> getMovieByGenreId(Long genreId, Pageable pageable) {
        if (!genreRepository.existsById(genreId)) {
            return Page.empty(pageable);
        }

        Page<Movie> moviesPage = movieRepository.findByGenreId(genreId, pageable);
        return moviesPage.map(movie -> {
            MovieDTO dto = modelMapper.map(movie, MovieDTO.class);
            dto.setGenres(
                    movie.getGenres()
                            .stream()
                            .map(Genre::getName)
                            .toList()
            );
            return dto;
        });
    }

    @Override
    public Page<MovieDTO> filter(MovieFilterForm form, Pageable pageable) {
        // Kiểm tra thể loại tồn tại theo tên genres
        if (form.getGenres() != null && !form.getGenres().isEmpty()) {
            // Lấy danh sách genre tồn tại trong db dựa trên tên
            List<Genre> genresInDb = genreRepository.findByNameIn(form.getGenres());

            if (genresInDb.size() != form.getGenres().size()) {
                // Nếu có ít hơn => 1 hoặc nhiều thể loại không tồn tại
                return Page.empty(pageable);
            }
        }

        Specification<Movie> spec = MovieSpecification.build(form);
        return movieRepository.findAll(spec, pageable)
                .map(movie -> {
                    MovieDTO dto = modelMapper.map(movie, MovieDTO.class);
                    dto.setGenres(
                            movie.getGenres()
                                    .stream()
                                    .map(Genre::getName)
                                    .toList()
                    );
                    return dto;
                });
    }



    @Override
    public MovieDTO getById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie not found"));
        MovieDTO dto = modelMapper.map(movie, MovieDTO.class);
        dto.setGenres(movie.getGenres()
                .stream()
                .map(Genre::getName)
                .toList());

        return dto;
    }

    @Override
    @Transactional
    public MovieDTO create(MovieCreateForm form) {
        Movie movie = modelMapper.map(form, Movie.class);

        List<Genre> genres = genreRepository.findByIdIn(form.getGenreIds());
        if (genres.size() != form.getGenreIds().size()) {
            throw new NotFoundException("Một hoặc nhiều thể loại không tồn tại");
        }

        movie.setGenres(new HashSet<>(genres));
        Movie saved = movieRepository.save(movie);

        MovieDTO dto = modelMapper.map(saved, MovieDTO.class);
        dto.setGenres(saved.getGenres()
                .stream()
                .map(Genre::getName)
                .toList());

        return dto;
    }


    @Override
    @Transactional
    public MovieDTO update(Long id, MovieUpdateForm form) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy phim"));

        modelMapper.map(form, movie);

        if (form.getGenreIds() != null && !form.getGenreIds().isEmpty()) {
            List<Genre> genres = genreRepository.findByIdIn(form.getGenreIds());
            if (genres.size() != form.getGenreIds().size()) {
                throw new NotFoundException("Một hoặc nhiều thể loại không tồn tại");
            }
            movie.setGenres(new HashSet<>(genres));
        }

        Movie updated = movieRepository.save(movie);
        MovieDTO dto = modelMapper.map(updated, MovieDTO.class);
        dto.setGenres(updated.getGenres()
                .stream()
                .map(Genre::getName)
                .toList());

        return dto;
    }


    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

}

