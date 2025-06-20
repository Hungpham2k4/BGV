package com.bagalaxy.BGV.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteDTO {
    private Long userId;
    private Long movieId;
    private String movieTitle;
}
