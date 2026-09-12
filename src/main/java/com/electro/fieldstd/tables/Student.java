package com.electro.fieldstd.tables;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(
        name = "students",
        indexes = {
                @Index(name = "idx_student_email", columnList = "email"),
                @Index(name = "idx_student_department", columnList = "department"),
                @Index(name = "idx_student_last_name", columnList = "last_name")
        }
)
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 20)
    private String phone;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 100)
    private String department;

    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
