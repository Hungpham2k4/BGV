package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.CommentDTO;
import com.bagalaxy.BGV.form.comment.CommentCreateForm;
import com.bagalaxy.BGV.form.comment.CommentUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<CommentDTO> getByMovieId(Long movieId, Pageable pageable);

    CommentDTO create(Long userId, CommentCreateForm form);

    CommentDTO update(Long id, CommentUpdateForm form);

    void delete(Long id);
}
