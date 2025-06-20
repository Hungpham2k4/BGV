package com.bagalaxy.BGV.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RatingDTO {
    private Long id;
    private int score;
    private String username;
    private String review;
    private Long userId;
    private Long movieId;
}
