package com.bagalaxy.BGV.form.genre;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class GenreUpdateForm {

    @Length(max = 50)
    private String name;
}
