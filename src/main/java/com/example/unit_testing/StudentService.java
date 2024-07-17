package com.example.unit_testing;

import com.example.unit_testing.student.Role;
import com.example.unit_testing.student.Student;
import com.example.unit_testing.studentRepository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;
    public void findAllStudents(){
        repository.findAll();
    }
    public void addStudent(Student student){
        boolean studentByEmail = repository.findStudentByEmail(student.getEmail());
        if (!studentByEmail){
            throw new UsernameNotFoundException("user not found");
        }
        repository.save(student);
    }
    public void deleteStudent(Long id){
        Optional<Student> byId = repository.findById(id);
        if (!byId.isPresent()){
            throw new UsernameNotFoundException("not on the file");
        }
        repository.deleteById(id);
    }
    public Student updateStudent(Long id,Student student){
        Optional<Student> byId = repository.findById(id);
        if (!byId.isPresent()){
            throw new UsernameNotFoundException("not found");
        }
        Student stud = byId.get();
        stud.setEmail(student.getEmail());
        stud.setRole(student.getRole());
        stud.setFirstName(student.getFirstName());
        stud.setLastName(student.getLastName());
        repository.save(stud);
        return stud;
    }

}
