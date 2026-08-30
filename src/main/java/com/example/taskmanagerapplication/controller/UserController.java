package com.example.taskmanagerapplication.controller;

import com.example.taskmanagerapplication.dto.create.UserCreateDTO;
import com.example.taskmanagerapplication.dto.response.UserResponseDTO;
import com.example.taskmanagerapplication.dto.update.UserPasswordUpdateDTO;
import com.example.taskmanagerapplication.dto.update.UserUpdateDTO;
import com.example.taskmanagerapplication.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getUsers(@RequestParam(defaultValue = "createdAt", required = false) String sortBy, @RequestParam(defaultValue = "true", required = false) String ascending) {
        return ResponseEntity.ok(userService.getUsers(sortBy, ascending));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userAuthorization.isOwner(#id, authentication)")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.getUser(id);
        if (userResponseDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN') or @userAuthorization.isOwner(#email, authentication)")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        UserResponseDTO userResponseDTO = userService.getUserByEmail(email);
        if (userResponseDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Validated UserCreateDTO userCreateDTO) {
        if (userService.createUser(userCreateDTO))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userAuthorization.isOwner(#id, authentication)")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userAuthorization.isOwner(#id, authentication)")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @Validated @RequestBody UserUpdateDTO userUpdateDTO) {
        if (userService.updateUser(id, userUpdateDTO))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/password")
    @PreAuthorize("@userAuthorization.isOwner(#id, authentication)")
    public ResponseEntity<Void> updateUserPassword(@PathVariable Long id, @Validated @RequestBody UserPasswordUpdateDTO userPasswordUpdateDTO) {
        if (userService.updateUserPassword(id, userPasswordUpdateDTO))
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}
