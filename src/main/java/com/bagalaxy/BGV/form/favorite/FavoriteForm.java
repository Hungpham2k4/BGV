package com.bagalaxy.BGV.form.favorite;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteForm {

    @NotNull
    private Long movieId;
}
