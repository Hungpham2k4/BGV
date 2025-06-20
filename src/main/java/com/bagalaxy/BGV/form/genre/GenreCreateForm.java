package com.bagalaxy.BGV.form.genre;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class GenreCreateForm {

    @NotBlank
    @Length(max = 50)
    private String name;
}

