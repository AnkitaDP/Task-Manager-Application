package com.example.taskmanagerapplication.service;

import com.example.taskmanagerapplication.model.User_;
import com.example.taskmanagerapplication.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthorization {

    private final UserRepo userRepo;

    public boolean isOwner(Long userId, Authentication authentication) {
        User_ user = userRepo.findById(userId).orElse(null);
        if (user == null)
            return false;
        return user.getEmail().equals(authentication.getName());
    }

    public boolean isOwner(String email, Authentication authentication) {
        User_ user = userRepo.findByEmail(email);
        if (user == null)
            return false;
        return user.getEmail().equals(authentication.getName());
    }
}