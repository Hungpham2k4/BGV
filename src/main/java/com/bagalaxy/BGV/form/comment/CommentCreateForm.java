package com.bagalaxy.BGV.form.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CommentCreateForm {

    @NotNull
    private Long movieId;

    @NotBlank
    @Length(max = 500)
    private String content;
}
