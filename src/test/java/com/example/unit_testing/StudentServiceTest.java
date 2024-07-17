package com.example.unit_testing;

import com.example.unit_testing.student.Role;
import com.example.unit_testing.student.Student;
import com.example.unit_testing.studentRepository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService underTest;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllStudents() {
        // when
        underTest.findAllStudents();
        //then
        verify(studentRepository).findAll();
    }

    @Test
    void CanAddStudent() {
        // given
        Student student = new Student("jerry","thulare","jerry@gmail.com","123456", Role.USER);
        when(studentRepository.findStudentByEmail(student.getEmail())).thenReturn(true);
        underTest.addStudent(student);
        // when
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        // then
       assertThat(capturedStudent).isEqualTo(student);
    }
    @Test
    public void willThrowErrorWhenAddingStudent(){
      // given
        Student student = new Student("jerry","thulare","jerry@gmail.com","123456", Role.USER);
         when(studentRepository.findStudentByEmail(student.getEmail())).thenReturn(false);

         // when

         assertThatThrownBy(()-> underTest.addStudent(student))
                 .isInstanceOf(UsernameNotFoundException.class)
                 .hasMessageContaining("user not found");
         verify(studentRepository , never()).save(any());
    }

    @Test
    void deleteStudent() {
        // given
        Student student = new Student("jerry","thulare","jerry@gmail.com","123456", Role.USER);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        // when
        underTest.deleteStudent(student.getId());
        //then
        verify(studentRepository).deleteById(any());
    }
    @Test
    public void willThrownAnErrorWhenDeletingStudent(){
        // given
        Student student = new Student("jerry","thulare","jerry@gmail.com","123456", Role.USER);
       when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
       // when
        assertThatThrownBy(()-> underTest.deleteStudent(student.getId()))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("not on the file");
        // then
        verify(studentRepository,never()).deleteById(any());
    }

    @Test
    void updateStudent() {
    }
}