package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.UserDTO;
import com.bagalaxy.BGV.form.user.UserRegisterForm;
import com.bagalaxy.BGV.form.user.UserUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    Page<UserDTO> getAll(Pageable pageable);
    UserDTO getById(Long id);
    @Transactional
    UserDTO update(Long id, UserUpdateForm form);
    void delete(Long id);
    void resetPassword(String email, String newPassword);
    UserDetails loadUserByUsername(String username);
    Page<UserDTO> searchByUsernameOrEmail(String keyword, Pageable pageable);
}
