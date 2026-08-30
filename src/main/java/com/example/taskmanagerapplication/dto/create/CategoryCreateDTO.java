package com.example.taskmanagerapplication.dto.create;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreateDTO {
    @NotBlank
    private String name;
}
