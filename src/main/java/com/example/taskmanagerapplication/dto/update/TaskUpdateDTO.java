package com.example.taskmanagerapplication.dto.update;

import com.example.taskmanagerapplication.enums.Priority;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskUpdateDTO {
    private String description;
    private boolean isCompleted;
    private Priority priority;
    private LocalDateTime deadline;
}