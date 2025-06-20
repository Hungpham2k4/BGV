package com.bagalaxy.BGV.form.movie;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MovieUpdateForm {

    private String title;
    private String description;
    private String director;
    private String thumbnailUrl;
    private String videoUrl;
    private LocalDate releaseDate;
    private Integer duration;
    private List<Long> genreIds;
}
