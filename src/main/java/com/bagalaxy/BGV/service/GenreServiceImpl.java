package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.GenreDTO;
import com.bagalaxy.BGV.entity.Genre;
import com.bagalaxy.BGV.form.genre.GenreCreateForm;
import com.bagalaxy.BGV.form.genre.GenreUpdateForm;
import com.bagalaxy.BGV.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GenreDTO> getAll() {
        return genreRepository.findAll().stream()
                .map(genre -> modelMapper.map(genre, GenreDTO.class))
                .toList();
    }

    @Override
    public GenreDTO getById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found"));
        return modelMapper.map(genre, GenreDTO.class);
    }

    @Override
    public GenreDTO create(GenreCreateForm form) {
        if (genreRepository.existsByName(form.getName())) {
            throw new IllegalStateException("Genre already exists");
        }
        Genre genre = new Genre();
        genre.setName(form.getName());

        Genre saved = genreRepository.save(genre);
        return modelMapper.map(saved, GenreDTO.class);
    }

    @Override
    public GenreDTO update(Long id, GenreUpdateForm form) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        if (form.getName() != null && !form.getName().isBlank()) {
            genre.setName(form.getName());
        }

        Genre updated = genreRepository.save(genre);
        return modelMapper.map(updated, GenreDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new NotFoundException("Genre not found");
        }
        genreRepository.deleteById(id);
    }
}
