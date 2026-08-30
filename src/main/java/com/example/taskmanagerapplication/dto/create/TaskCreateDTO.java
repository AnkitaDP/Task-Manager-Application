package com.example.taskmanagerapplication.dto.create;

import com.example.taskmanagerapplication.enums.Priority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskCreateDTO {
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private Priority priority;
    @NotNull
    @Future
    private LocalDateTime deadline;
    @NotNull
    private Long categoryId;
}
