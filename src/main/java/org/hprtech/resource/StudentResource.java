package org.hprtech.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.hprtech.entity.StudentRepository;
import org.hprtech.repository.Student;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class StudentResource {
    @Inject
    StudentRepository studentRepository;

//    @GET
//    @Path("getStudentList")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getStudentList(){
//        List<Student> studentList =studentRepository.listAll();
//        return Response.ok(studentList).build();
//    }
//
//    @GET
//    @Path("getCsStudentList")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getCsStudentList(){
//        List<Student> studentList =studentRepository.listAll();
//        List<Student>csStudentList = new ArrayList<>();
//        studentList.forEach(student -> {
//            if(student.getBranch().equals("CS"))
//                csStudentList.add(student);
//        });
//        return Response.ok(csStudentList).build();
//    }

    @POST
    @Path("addStudent")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@RequestBody Student student) {
        studentRepository.persist(student);
        if (studentRepository.isPersistent(student)) {
            return Response.created(URI.create("/student/" + student.getStudentId())).build();
        } else {
            return Response.ok(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("id")Long id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            return Response.ok(Response.status(Response.Status.NOT_FOUND)).build();
        } else {
            return Response.ok(student).build();
        }
    }

    @GET
    @Path("getAllStudent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentList(){
        List<Student> studentList =studentRepository.listAll();
        return Response.ok(studentList).build();
    }

}
