package com.bagalaxy.BGV.controller;

import com.bagalaxy.BGV.dto.CommentDTO;
import com.bagalaxy.BGV.form.comment.CommentCreateForm;
import com.bagalaxy.BGV.form.comment.CommentUpdateForm;
import com.bagalaxy.BGV.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> create(@RequestParam Long userId,
                                             @RequestBody @Valid CommentCreateForm form) {
        return ResponseEntity.ok(commentService.create(userId, form));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable Long id, @RequestBody @Valid CommentUpdateForm form) {
        return ResponseEntity.ok(commentService.update(id, form));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Page<CommentDTO>> getByMovie(@PathVariable Long movieId, Pageable pageable) {
        return ResponseEntity.ok(commentService.getByMovieId(movieId, pageable));
    }
}