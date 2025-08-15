package com.dk.userservice.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private String id;

    private String userName;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private LocalDateTime dateOfBirth;

    private String gender;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}