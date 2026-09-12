package com.electro.fieldstd.repositor;


import com.electro.fieldstd.tables.Gender;
import com.electro.fieldstd.tables.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository
        extends JpaRepository<Student, UUID>,
        JpaSpecificationExecutor<Student> {

    boolean existsByEmail(String email);

    Optional<Student> findByEmail(String email);

    Page<Student> findByFirstNameContainingIgnoreCase(
            String firstName,
            Pageable pageable
    );

    Page<Student> findByDepartment(
            String department,
            Pageable pageable
    );

    Page<Student> findByGender(
            Gender gender,
            Pageable pageable
    );
}
