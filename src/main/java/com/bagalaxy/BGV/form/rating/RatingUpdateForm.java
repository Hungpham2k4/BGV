package com.bagalaxy.BGV.form.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class RatingUpdateForm {

    @Min(1)
    @Max(5)
    private int score;

    @Length(max = 500)
    private String review;
}
