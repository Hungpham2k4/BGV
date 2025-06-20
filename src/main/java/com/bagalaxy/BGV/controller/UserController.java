package com.bagalaxy.BGV.controller;

import com.bagalaxy.BGV.dto.UserDTO;
import com.bagalaxy.BGV.form.user.UserRegisterForm;
import com.bagalaxy.BGV.form.user.UserUpdateForm;
import com.bagalaxy.BGV.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(userService.getAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> search(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(userService.searchByUsernameOrEmail(keyword, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody @Valid UserUpdateForm form) {
        return ResponseEntity.ok(userService.update(id, form));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}