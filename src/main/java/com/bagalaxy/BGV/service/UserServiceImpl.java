package com.bagalaxy.BGV.service;

import com.bagalaxy.BGV.dto.UserDTO;
import com.bagalaxy.BGV.entity.Role;
import com.bagalaxy.BGV.entity.User;
import com.bagalaxy.BGV.form.user.UserUpdateForm;
import com.bagalaxy.BGV.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService , UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<UserDTO> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO update(Long id, UserUpdateForm userUpdateForm) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userUpdateForm.getName());
            user.setEmail(userUpdateForm.getEmail());
            if (!user.getPassword().equals(userUpdateForm.getPassword())) {
                user.setPassword(passwordEncoder.encode(userUpdateForm.getPassword()));
            } else {
                user.setPassword(user.getPassword());
            }
            user.setRole(Role.valueOf(userUpdateForm.getRole()));
            userRepository.save(user);
        }
        return modelMapper.map(userUpdateForm, UserDTO.class);
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserDTO> searchByUsernameOrEmail(String keyword, Pageable pageable) {
        Page<User> users = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, pageable);
        return users.map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
//        String role = user.getRole().name();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
