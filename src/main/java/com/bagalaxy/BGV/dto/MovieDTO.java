package com.bagalaxy.BGV.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class MovieDTO {
    private Long id;
    private String title;
    private String description;
    private String director;
    private String thumbnailUrl;
    private String videoUrl;
    private LocalDate releaseDate;
    private int duration; // phút
    private List<String> genres; // tên thể loại
}
