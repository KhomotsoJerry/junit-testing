package com.example.unit_testing.studentRepository;

import com.example.unit_testing.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean findStudentByEmail(String email);
}
