package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.GenreDTO;
import com.bagalaxy.BGV.form.genre.GenreCreateForm;
import com.bagalaxy.BGV.form.genre.GenreUpdateForm;

import java.util.List;

public interface GenreService {
    List<GenreDTO> getAll();

    GenreDTO getById(Long id);

    GenreDTO create(GenreCreateForm form);

    GenreDTO update(Long id, GenreUpdateForm form);

    void delete(Long id);
}
