package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.CommentDTO;
import com.bagalaxy.BGV.entity.Comment;
import com.bagalaxy.BGV.entity.Movie;
import com.bagalaxy.BGV.entity.User;
import com.bagalaxy.BGV.form.comment.CommentCreateForm;
import com.bagalaxy.BGV.form.comment.CommentUpdateForm;
import com.bagalaxy.BGV.repository.CommentRepository;
import com.bagalaxy.BGV.repository.MovieRepository;
import com.bagalaxy.BGV.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<CommentDTO> getByMovieId(Long movieId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByMovieIdOrderByCreatedAtDesc(movieId, pageable);
        return comments.map(comment -> {
            CommentDTO dto = modelMapper.map(comment, CommentDTO.class);
            dto.setUsername(comment.getUser().getUsername());
            dto.setMovieId(comment.getMovie().getId());
            return dto;
        });
    }

    @Override
    public CommentDTO create(Long userId, CommentCreateForm form) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Movie movie = movieRepository.findById(form.getMovieId())
                .orElseThrow(() -> new NotFoundException("Movie not found"));

        Comment comment = new Comment();
        comment.setContent(form.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setMovie(movie);

        Comment saved = commentRepository.save(comment);
        CommentDTO dto = modelMapper.map(saved, CommentDTO.class);
        dto.setUsername(user.getUsername());
        dto.setMovieId(movie.getId());
        return dto;
    }

    @Override
    public CommentDTO update(Long id, CommentUpdateForm form) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        comment.setContent(form.getContent());
        Comment updated = commentRepository.save(comment);

        CommentDTO dto = modelMapper.map(updated, CommentDTO.class);
        dto.setUsername(updated.getUser().getUsername());
        dto.setMovieId(updated.getMovie().getId());
        return dto;
    }

    @Override
    public void delete(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new NotFoundException("Comment not found");
        }
        commentRepository.deleteById(id);
    }
}

