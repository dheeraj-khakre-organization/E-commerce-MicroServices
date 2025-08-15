package com.dk.userservice.service;

import com.dk.userservice.dtos.UserRequestDTO;
import com.dk.userservice.dtos.UserResponseDTO;
import com.dk.userservice.mapper.UserMapper;
import com.dk.userservice.models.User;
import com.dk.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO registerUser(UserRequestDTO dto) {
        User user = UserMapper.toEntity(dto);
        user.setRoles(List.of("USER"));
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return UserMapper.toResponse(userRepository.save(user));
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponseDTO getUserById(String id) {
        return userRepository.findById(id)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponseDTO getUserByUsername(String username) {
        return userRepository.findByUserName(username)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponseDTO updateUser(String id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setGender(User.Gender.valueOf(dto.getGender().toUpperCase()));
        user.setActive(dto.getActive());
        return UserMapper.toResponse(userRepository.save(user));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}