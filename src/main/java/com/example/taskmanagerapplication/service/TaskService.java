package com.example.taskmanagerapplication.service;

import com.example.taskmanagerapplication.dto.create.TaskCreateDTO;
import com.example.taskmanagerapplication.dto.response.TaskResponseDTO;
import com.example.taskmanagerapplication.dto.update.TaskUpdateDTO;
import com.example.taskmanagerapplication.enums.Priority;
import com.example.taskmanagerapplication.mapper.TaskMapper;
import com.example.taskmanagerapplication.model.Category;
import com.example.taskmanagerapplication.model.Task;
import com.example.taskmanagerapplication.model.User_;
import com.example.taskmanagerapplication.repo.CategoryRepo;
import com.example.taskmanagerapplication.repo.TaskRepo;
import com.example.taskmanagerapplication.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepo taskRepo;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final TaskMapper taskMapper;

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasks(Long userId, String sortBy, String ascending, String isCompleted, String priority, String categoryId) {
        Sort.Direction direction = (ascending.equals("true")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<TaskResponseDTO> taskDTOs = taskRepo.findByUserId(userId, Sort.by(direction, sortBy)).stream().map(taskMapper::toDTO).toList();
        boolean completionStatus;
        Priority priority_;
        if (!isCompleted.equals("all")) {
            completionStatus = isCompleted.equals("true");
            taskDTOs = taskDTOs.stream().filter(task -> task.isCompleted() == completionStatus).toList();
        }
        if (!priority.equals("all")) {
            if (priority.equalsIgnoreCase("high"))
                priority_ = Priority.HIGH;
            else if (priority.equalsIgnoreCase(("low")))
                priority_ = Priority.LOW;
            else
                priority_ = Priority.MEDIUM;
            taskDTOs = taskDTOs.stream().filter(task -> task.getPriority().equals(priority_)).toList();
        }
        if (!categoryId.equals("all"))
            taskDTOs = taskDTOs.stream().filter(task -> Objects.equals(task.getCategoryId(), Long.valueOf(categoryId))).toList();
        return taskDTOs;
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO getTask(Long userId, Long id) {
        return taskMapper.toDTO(taskRepo.findByIdAndUserId(id, userId));
    }

    @Transactional
    public boolean createTask(Long userId, @Validated TaskCreateDTO taskCreateDTO) {
        User_ user = userRepo.findById(userId).orElse(null);
        Category category = categoryRepo.findById(taskCreateDTO.getCategoryId()).orElse(null);
        if (user == null || category == null || taskRepo.existsByTitleAndUserId(taskCreateDTO.getTitle(), userId))
            return false;
        Task task = taskMapper.toEntity(taskCreateDTO);
        task.setCategory(category);
        task.setUser(user);
        taskRepo.save(task);
        return true;
    }

    @Transactional
    public boolean deleteTask(Long userId, Long id) {
        if (!userRepo.existsById(userId) || !taskRepo.existsByIdAndUserId(id, userId))
            return false;
        taskRepo.deleteByIdAndUserId(id, userId);
        return true;
    }

    @Transactional
    public boolean updateTask(Long userId, Long id, TaskUpdateDTO taskUpdateDTO) {
        Task task = taskRepo.findByIdAndUserId(id, userId);
        if (task == null)
            return false;
        if (!taskUpdateDTO.isCompleted() && taskUpdateDTO.getDeadline() != null && taskUpdateDTO.getDeadline().isBefore(LocalDateTime.now()))
            return false;
        taskMapper.updateTaskFromDTO(taskUpdateDTO, task);
        return true;
    }
}