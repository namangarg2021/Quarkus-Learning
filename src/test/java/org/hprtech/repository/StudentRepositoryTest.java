package org.hprtech.repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.hprtech.entity.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class StudentRepositoryTest {
    @Inject
    StudentRepository studentRepository;
    @Test
    void listAll(){
         List<Student> studentList =  studentRepository.listAll();

         assertFalse(studentList.isEmpty());
         assertEquals(studentList.size(),6);
         assertEquals(studentList.get(0).getName(),"Vikash");
    }

    @Test
    void findById(){
       Student student =  studentRepository.findById(6L);

        assertNotNull(student);
        assertEquals(student.getStudentId(),6L);
        assertEquals(student.getName(),"Rahul");
        assertEquals(student.getBranch(),"ME");

    }
    @Test
    void getStudentByBranch(){
        List<Student> studentList =  studentRepository.getStudentByBranch("EE");

        assertFalse(studentList.isEmpty());
        assertEquals(studentList.size(),2);

    }
}