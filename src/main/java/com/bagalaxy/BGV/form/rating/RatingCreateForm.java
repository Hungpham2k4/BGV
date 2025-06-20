package com.bagalaxy.BGV.form.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class RatingCreateForm {

    @NotNull
    private Long movieId;

    @Min(1)
    @Max(5)
    private int score;

    @Length(max = 500)
    private String review;
}
