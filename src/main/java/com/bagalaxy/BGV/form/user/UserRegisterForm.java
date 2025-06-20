package com.bagalaxy.BGV.form.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserRegisterForm {

    @NotBlank
    @Length(max = 50)
    private String username;

    @NotBlank
    @Email(message = "Email không hợp lệ")
    @Length(max = 100)
    private String email;

    @NotBlank
    @Length(min = 8, max = 32, message = "Mật khẩu từ 8–32 ký tự")
    private String password;
}
