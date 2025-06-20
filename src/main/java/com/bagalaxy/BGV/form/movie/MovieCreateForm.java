package com.bagalaxy.BGV.form.movie;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MovieCreateForm {

    @NotBlank
    @Length(max = 100)
    private String title;

    @Length(max = 1000)
    private String description;

    private String director;

    @NotBlank
    private String thumbnailUrl;

    @NotBlank
    private String videoUrl;

    private LocalDate releaseDate;

    @Min(1)
    private int duration; // phút

    private List<Long> genreIds;
}
