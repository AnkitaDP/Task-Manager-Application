package com.example.taskmanagerapplication.service;

import com.example.taskmanagerapplication.dto.create.UserCreateDTO;
import com.example.taskmanagerapplication.dto.response.UserResponseDTO;
import com.example.taskmanagerapplication.dto.update.UserPasswordUpdateDTO;
import com.example.taskmanagerapplication.dto.update.UserUpdateDTO;
import com.example.taskmanagerapplication.mapper.UserMapper;
import com.example.taskmanagerapplication.model.User_;
import com.example.taskmanagerapplication.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getUsers(String sortBy, String ascending) {
        Sort.Direction direction = (ascending.equals("true")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return userRepo.findAll(Sort.by(direction, sortBy)).stream().map(userMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Long id) {
        return userMapper.toDTO(userRepo.findById(id).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(String email) {
        return userMapper.toDTO(userRepo.findByEmail(email));
    }

    @Transactional
    public boolean createUser(UserCreateDTO userCreateDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(7);
        if (userRepo.existsByEmail(userCreateDTO.getEmail()))
            return false;
        userCreateDTO.setPassword(encoder.encode(userCreateDTO.getPassword()));
        userRepo.save(userMapper.toEntity(userCreateDTO));
        return true;
    }

    @Transactional
    public boolean deleteUser(Long id) {
        if (!userRepo.existsById(id))
            return false;
        userRepo.deleteById(id);
        return true;
    }

    @Transactional
    public boolean updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User_ user = userRepo.findById(id).orElse(null);
        if (user == null)
            return false;
        String firstName = userUpdateDTO.getFirstName();
        String lastName = userUpdateDTO.getLastName();
        if ((firstName != null && firstName.isEmpty()) || (lastName != null && lastName.isEmpty()))
            return false;
        userMapper.updateUserFromDTO(userUpdateDTO, user);
        return true;
    }

    @Transactional
    public boolean updateUserPassword(Long id, UserPasswordUpdateDTO userPasswordUpdateDTO) {
        User_ user = userRepo.findById(id).orElse(null);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(7);
        String oldPassword = userPasswordUpdateDTO.getOldPassword();
        String newPassword = userPasswordUpdateDTO.getPassword();
        if (user == null || newPassword == null || !encoder.matches(oldPassword, user.getPassword()))
            return false;
        user.setPassword(encoder.encode(newPassword));
        return true;
    }
}