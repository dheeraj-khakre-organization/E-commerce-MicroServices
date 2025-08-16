package com.dk.userservice.mapper;

import com.dk.userservice.dtos.UserRequestDTO;
import com.dk.userservice.dtos.UserResponseDTO;
import com.dk.userservice.models.User;

public class UserMapper {

    public static UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender() != null ? user.getGender().name() : null)
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static User toEntity(UserRequestDTO dto) {
        return User.builder()
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender() != null ? User.Gender.valueOf(dto.getGender().toUpperCase()) : null)
                .active(dto.getActive())
                .build();
    }
}