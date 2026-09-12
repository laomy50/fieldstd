package com.electro.fieldstd.dto;

import com.electro.fieldstd.tables.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record StudentRequestDTO(

        @NotBlank(message = "First name is required")
        @Size(max = 100)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100)
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email")
        String email,

        @Size(max = 20)
        String phone,

        @Min(value = 1, message = "Age must be greater than 0")
        @Max(value = 120, message = "Invalid age")
        Integer age,

        Gender gender,

        String department,

        LocalDate dateOfBirth
) {
}
