package com.example.taskmanagerapplication.mapper;

import com.example.taskmanagerapplication.dto.create.TaskCreateDTO;
import com.example.taskmanagerapplication.dto.response.TaskResponseDTO;
import com.example.taskmanagerapplication.dto.update.TaskUpdateDTO;
import com.example.taskmanagerapplication.model.Task;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "category.id", target = "categoryId")
    TaskResponseDTO toDTO(Task task);
    Task toEntity(TaskCreateDTO taskCreateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromDTO(TaskUpdateDTO taskUpdateDTO, @MappingTarget Task task);
}
