package com.bagalaxy.BGV.form.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CommentUpdateForm {

    @NotBlank
    @Length(max = 500)
    private String content;
}
