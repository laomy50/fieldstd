package com.electro.fieldstd.service;

import com.electro.fieldstd.dto.StudentRequestDTO;
import com.electro.fieldstd.dto.StudentResponseDTO;
import com.electro.fieldstd.except.DuplicateResourceException;
import com.electro.fieldstd.except.ResourceNotFoundException;
import com.electro.fieldstd.repositor.StudentRepository;
import com.electro.fieldstd.tables.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;


    // =========================
    // GET ALL STUDENTS
    // =========================

    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> getAllStudents(
            Pageable pageable
    ) {

        return studentRepository.findAll(pageable)
                .map(this::toResponse);
    }


    // =========================
    // GET STUDENT BY UUID
    // =========================

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudent(UUID id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found: " + id
                        )
                );

        return toResponse(student);
    }


    // =========================
    // GET STUDENT BY EMAIL
    // =========================

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentByEmail(String email) {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with email: " + email
                        )
                );

        return toResponse(student);
    }


    // =========================
    // SEARCH BY FIRST NAME
    // =========================

    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> getStudentsByFirstName(
            String firstName,
            Pageable pageable
    ) {

        return studentRepository
                .findByFirstNameContainingIgnoreCase(
                        firstName,
                        pageable
                )
                .map(this::toResponse);
    }


    // =========================
    // GET BY DEPARTMENT
    // =========================

    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> getStudentsByDepartment(
            String department,
            Pageable pageable
    ) {

        return studentRepository
                .findByDepartment(department, pageable)
                .map(this::toResponse);
    }


    // =========================
    // GET BY GENDER
    // =========================

    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> getStudentsByGender(
            com.electro.fieldstd.tables.Gender gender,
            Pageable pageable
    ) {

        return studentRepository
                .findByGender(gender, pageable)
                .map(this::toResponse);
    }


    // =========================
    // CREATE STUDENT
    // =========================

    public StudentResponseDTO createStudent(
            StudentRequestDTO dto
    ) {

        if (studentRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        Student student = new Student();

        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setEmail(dto.email());
        student.setPhone(dto.phone());
        student.setAge(dto.age());
        student.setGender(dto.gender());
        student.setDepartment(dto.department());
        student.setDateOfBirth(dto.dateOfBirth());

        Student saved = studentRepository.save(student);

        return toResponse(saved);
    }


    // =========================
    // UPDATE STUDENT
    // =========================

    public StudentResponseDTO updateStudent(
            UUID id,
            StudentRequestDTO dto
    ) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found: " + id
                        )
                );

        // Check if email belongs to another student
        studentRepository.findByEmail(dto.email())
                .ifPresent(existingStudent -> {

                    if (!existingStudent.getId().equals(id)) {
                        throw new DuplicateResourceException(
                                "Email already exists"
                        );
                    }
                });

        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setEmail(dto.email());
        student.setPhone(dto.phone());
        student.setAge(dto.age());
        student.setGender(dto.gender());
        student.setDepartment(dto.department());
        student.setDateOfBirth(dto.dateOfBirth());

        Student updated = studentRepository.save(student);

        return toResponse(updated);
    }


    // =========================
    // DELETE STUDENT
    // =========================

    public void deleteStudent(UUID id) {

        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Student not found: " + id
            );
        }

        studentRepository.deleteById(id);
    }


    // =========================
    // ENTITY → RESPONSE DTO
    // =========================

    private StudentResponseDTO toResponse(Student student) {

        return new StudentResponseDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhone(),
                student.getAge(),
                student.getGender(),
                student.getDepartment(),
                student.getDateOfBirth()
        );
    }
}
