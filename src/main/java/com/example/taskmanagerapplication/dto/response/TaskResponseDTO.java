package com.example.taskmanagerapplication.dto.response;

import com.example.taskmanagerapplication.enums.Priority;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private boolean isCompleted;
    private Priority priority;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    private Long userId;
    private Long categoryId;
}
