package com.example.taskmanagerapplication.controller;

import com.example.taskmanagerapplication.dto.create.TaskCreateDTO;
import com.example.taskmanagerapplication.dto.response.TaskResponseDTO;
import com.example.taskmanagerapplication.dto.update.TaskUpdateDTO;
import com.example.taskmanagerapplication.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users/{userId}/tasks")
@PreAuthorize("@userAuthorization.isOwner(#userId, authentication)")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getTasks(@PathVariable Long userId, @RequestParam(defaultValue = "createdAt", required = false) String sortBy, @RequestParam(defaultValue = "true", required = false) String ascending, @RequestParam(defaultValue = "all", required = false) String isCompleted, @RequestParam(defaultValue = "all", required = false) String priority, @RequestParam(defaultValue = "all", required = false) String categoryId) {
        return ResponseEntity.ok(taskService.getTasks(userId, sortBy, ascending, isCompleted, priority, categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable Long userId, @PathVariable Long id) {
        TaskResponseDTO taskResponseDTO = taskService.getTask(userId, id);
        if (taskResponseDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(taskResponseDTO);
    }

    @PostMapping
    public ResponseEntity<Void> createTask(@PathVariable Long userId, @RequestBody @Validated TaskCreateDTO taskCreateDTO) {
        if (taskService.createTask(userId, taskCreateDTO))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long userId, @PathVariable Long id) {
        if (taskService.deleteTask(userId, id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable Long userId, @PathVariable Long id, @RequestBody TaskUpdateDTO taskUpdateDTO) {
        if (taskService.updateTask(userId, id, taskUpdateDTO))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}