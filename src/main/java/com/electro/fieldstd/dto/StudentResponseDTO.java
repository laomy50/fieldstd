package com.electro.fieldstd.dto;

import com.electro.fieldstd.tables.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record StudentResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone,
        Integer age,
        Gender gender,
        String department,
        LocalDate dateOfBirth
) {
}

