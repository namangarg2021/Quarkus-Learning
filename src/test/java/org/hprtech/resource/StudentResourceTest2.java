package org.hprtech.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.hprtech.entity.StudentRepository;
import org.hprtech.repository.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.discovery.SelectorResolver;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class StudentResourceTest2 {

    @Inject
    StudentResource studentResource;

    @InjectMock
    StudentRepository studentRepository;

    Student student;

    @BeforeEach
    void setUp(){
        student = new Student();
        student.setName("Aakash");
        student.setBranch("CS");
    }


    @Test
    void addStudent() {
        Student s = new Student(1L,"Rahul","CS");

        Mockito.doNothing()
                .when(studentRepository)
                .persist(s);

        Mockito.when(studentRepository.isPersistent(s))
                .thenReturn(true);

        Response response = studentResource.addStudent(s);

        assertNotNull(response);
        assertNull(response.getEntity());
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        assertNotNull(response.getLocation());

    }

    @Test
    void getStudentById() {
        Student student1 = new Student(4L,"Rajesh","ME");
//        Mockito.when(studentRepository.findById(4L))
//                .thenReturn(student1);
        Mockito.when(studentRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(student1);
        Response response = studentResource.getStudentById(4L);

        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(response.getStatus(),Response.Status.OK.getStatusCode());

        Student sr = (Student) response.getEntity();
        assertEquals(sr.getName(), "Rajesh");
        assertEquals(sr.getBranch(), "ME");
    }
    @Test //negative test case
    void getStudentByIdKO() {
        Mockito.when(studentRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(null);
        Response response = studentResource.getStudentById(5L);

        assertNotNull(response);
        assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    void getStudentList() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L,"Shruti","CS"));
        studentList.add(new Student(2L,"Rahul","CS"));
        studentList.add(new Student(3L,"Aakanksha","ME"));
        Mockito.when(studentRepository.listAll())
                .thenReturn(studentList);

        Response response = studentResource.getStudentList();

        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(response.getStatus(),Response.Status.OK.getStatusCode());

        List<Student> studentList1 = (List<Student>) response.getEntity();
        assertEquals(studentList1.size(),3);

    }

    @Test
    void updateStudent() {
        Student student1 = new Student(4L,"Rajesh","ME");

        Mockito.when(studentRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(student1);

        Student newUpdateStudent = new Student();
        newUpdateStudent.setName("Mahesh");

        Response response = studentResource.updateStudent(newUpdateStudent,4L);

        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(response.getStatus(),Response.Status.OK.getStatusCode());

        Student sr = (Student) response.getEntity();
        assertEquals(sr.getName(), "Mahesh");
        assertEquals(sr.getBranch(), "ME");

    }

}