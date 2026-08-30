package com.example.taskmanagerapplication.repo;

import com.example.taskmanagerapplication.model.Task;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId, Sort sortBy);
    Task findByIdAndUserId(Long id, Long userId);
    boolean existsByTitleAndUserId(@NotBlank String title, Long userId);
    boolean existsByIdAndUserId(Long id, Long userId);
    void deleteByIdAndUserId(Long id, Long userId);
}
