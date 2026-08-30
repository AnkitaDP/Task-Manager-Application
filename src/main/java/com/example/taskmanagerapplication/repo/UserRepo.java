package com.example.taskmanagerapplication.repo;

import com.example.taskmanagerapplication.model.User_;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User_, Long> {
    boolean existsByEmail(@Email String email);
    User_ findByEmail(String email);
}
