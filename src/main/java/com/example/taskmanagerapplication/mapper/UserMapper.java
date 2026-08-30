package com.example.taskmanagerapplication.mapper;

import com.example.taskmanagerapplication.dto.create.UserCreateDTO;
import com.example.taskmanagerapplication.dto.response.UserResponseDTO;
import com.example.taskmanagerapplication.dto.update.UserPasswordUpdateDTO;
import com.example.taskmanagerapplication.dto.update.UserUpdateDTO;
import com.example.taskmanagerapplication.model.User_;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface UserMapper {
    UserResponseDTO toDTO(User_ user);
    User_ toEntity(UserCreateDTO userCreateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDTO(UserUpdateDTO userUpdateDTO, @MappingTarget User_ user);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserPasswordFromDTO(UserPasswordUpdateDTO userPasswordUpdateDTO, @MappingTarget User_ user);
}
