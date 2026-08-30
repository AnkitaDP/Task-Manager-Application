package com.example.taskmanagerapplication.service;

import com.example.taskmanagerapplication.model.User_;
import com.example.taskmanagerapplication.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public @NullMarked UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User_ user = userRepo.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("Error: User not found with the email " + email);
        return new UserPrincipal(user);
    }
}