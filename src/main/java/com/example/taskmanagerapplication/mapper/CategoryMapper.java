package com.example.taskmanagerapplication.mapper;

import com.example.taskmanagerapplication.dto.create.CategoryCreateDTO;
import com.example.taskmanagerapplication.dto.response.CategoryResponseDTO;
import com.example.taskmanagerapplication.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface CategoryMapper {
    CategoryResponseDTO toDTO(Category category);
    Category toEntity(CategoryCreateDTO categoryCreateDTO);
}
