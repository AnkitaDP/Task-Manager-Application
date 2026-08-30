package com.example.taskmanagerapplication.repo;

import com.example.taskmanagerapplication.model.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    boolean existsByName(@NotBlank String name);
}
