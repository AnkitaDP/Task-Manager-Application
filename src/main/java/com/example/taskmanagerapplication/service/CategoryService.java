package com.example.taskmanagerapplication.service;

import com.example.taskmanagerapplication.dto.create.CategoryCreateDTO;
import com.example.taskmanagerapplication.dto.response.CategoryResponseDTO;
import com.example.taskmanagerapplication.mapper.CategoryMapper;
import com.example.taskmanagerapplication.model.Category;
import com.example.taskmanagerapplication.repo.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getCategories() {
        return categoryRepo.findAll().stream().map(categoryMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategory(Long id) {
        return categoryMapper.toDTO(categoryRepo.findById(id).orElse(null));
    }

    @Transactional
    public boolean createCategory(CategoryCreateDTO categoryCreateDTO) {
        if (categoryRepo.existsByName(categoryCreateDTO.getName()))
            return false;
        categoryRepo.save(categoryMapper.toEntity(categoryCreateDTO));
        return true;
    }

    @Transactional
    public boolean deleteCategory(Long id) {
        Category category = categoryRepo.findById(id).orElse(null);
        if (category == null || !category.getTasks().isEmpty())
            return false;
        categoryRepo.deleteById(id);
        return true;
    }
}
