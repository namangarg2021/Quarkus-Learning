package org.hprtech.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.hprtech.entity.StudentRepository;
import org.hprtech.repository.Student;

import java.util.ArrayList;
import java.util.List;

@Path("/")
public class StudentResource {
    @Inject
    StudentRepository studentRepository;

    @GET
    @Path("getStudentList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentList(){
        List<Student> studentList =studentRepository.listAll();
        return Response.ok(studentList).build();
    }

    @GET
    @Path("getCsStudentList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCsStudentList(){
        List<Student> studentList =studentRepository.listAll();
        List<Student>csStudentList = new ArrayList<>();
        studentList.forEach(student -> {
            if(student.getBranch().equals("CS"))
                csStudentList.add(student);
        });
        return Response.ok(csStudentList).build();
    }

    @POST
    @Path("addStudent")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@RequestBody Student student){
        studentRepository.persist(student);
        return Response.ok(student).build();
    }
}
