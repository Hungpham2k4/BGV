package com.bagalaxy.BGV.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String username;
    private Long movieId;
}
