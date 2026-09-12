package com.electro.fieldstd.cotroller;


import com.electro.fieldstd.dto.StudentRequestDTO;
import com.electro.fieldstd.dto.StudentResponseDTO;
import com.electro.fieldstd.service.StudentService;
import com.electro.fieldstd.tables.Gender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // =========================
    // CREATE
    // =========================

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(
            @Valid @RequestBody StudentRequestDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.createStudent(dto));
    }


    // =========================
    // GET ALL
    // =========================

    @GetMapping
    public ResponseEntity<Page<StudentResponseDTO>> getAllStudents(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "20")
            int size,

            @RequestParam(defaultValue = "lastName")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ) {

        size = Math.min(size, 100);

        if (page < 0) {
            page = 0;
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                studentService.getAllStudents(pageable)
        );
    }


    // =========================
    // GET BY UUID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudent(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                studentService.getStudent(id)
        );
    }


    // =========================
    // GET BY EMAIL
    // =========================

    @GetMapping("/email/{email}")
    public ResponseEntity<StudentResponseDTO> getByEmail(
            @PathVariable String email
    ) {

        return ResponseEntity.ok(
                studentService.getStudentByEmail(email)
        );
    }


    // =========================
    // SEARCH BY FIRST NAME
    // =========================

    @GetMapping("/search/name")
    public ResponseEntity<Page<StudentResponseDTO>> searchByFirstName(

            @RequestParam String firstName,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "20")
            int size
    ) {

        size = Math.min(size, 100);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("firstName").ascending()
                );

        return ResponseEntity.ok(
                studentService.getStudentsByFirstName(
                        firstName,
                        pageable
                )
        );
    }


    // =========================
    // GET BY DEPARTMENT
    // =========================

    @GetMapping("/department/{department}")
    public ResponseEntity<Page<StudentResponseDTO>> getByDepartment(

            @PathVariable String department,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "20")
            int size
    ) {

        size = Math.min(size, 100);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("lastName").ascending()
                );

        return ResponseEntity.ok(
                studentService.getStudentsByDepartment(
                        department,
                        pageable
                )
        );
    }


    // =========================
    // GET BY GENDER
    // =========================

    @GetMapping("/gender/{gender}")
    public ResponseEntity<Page<StudentResponseDTO>> getByGender(

            @PathVariable Gender gender,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "20")
            int size
    ) {

        size = Math.min(size, 100);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("lastName").ascending()
                );

        return ResponseEntity.ok(
                studentService.getStudentsByGender(
                        gender,
                        pageable
                )
        );
    }


    // =========================
    // UPDATE
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(

            @PathVariable UUID id,

            @Valid @RequestBody StudentRequestDTO dto
    ) {

        return ResponseEntity.ok(
                studentService.updateStudent(id, dto)
        );
    }


    // =========================
    // DELETE
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable UUID id
    ) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }
}
