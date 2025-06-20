package com.bagalaxy.BGV.form.movie;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MovieFilterForm {

    private String keyword;

    private List<String> genres;

    private LocalDate fromDate;

    private LocalDate toDate;

    private Integer minDuration;

    private Integer maxDuration;
}
