package com.example.taskmanagerapplication.dto.update;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordUpdateDTO {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String password;
}
