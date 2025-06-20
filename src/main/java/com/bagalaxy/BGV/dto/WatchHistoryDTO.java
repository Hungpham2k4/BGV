package com.bagalaxy.BGV.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WatchHistoryDTO {
    private Long id;
    private Long movieId;
    private String movieTitle;
    private int lastPosition; // giây
    private LocalDateTime watchedAt;
}
