package com.example.taskmanagerapplication.controller;

import com.example.taskmanagerapplication.dto.create.CategoryCreateDTO;
import com.example.taskmanagerapplication.dto.response.CategoryResponseDTO;
import com.example.taskmanagerapplication.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable Long id) {
        CategoryResponseDTO categoryResponseDTO = categoryService.getCategory(id);
        if (categoryResponseDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoryResponseDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createCategory(@RequestBody @Validated CategoryCreateDTO categoryCreateDTO) {
        if (categoryService.createCategory(categoryCreateDTO))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (categoryService.deleteCategory(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
