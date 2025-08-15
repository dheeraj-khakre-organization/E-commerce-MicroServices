package com.dk.userservice.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20)
    private String userName;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8)
    private String password;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number is invalid")
    private String phone;

    @Past(message = "Date of birth must be in the past")
    private LocalDateTime dateOfBirth;

    private String gender; // Accepts "MALE", "FEMALE", "OTHER"

    private Boolean active = true;
}