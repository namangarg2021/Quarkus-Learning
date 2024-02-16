package org.hprtech.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.hprtech.repository.StudentRepository;
import org.hprtech.entity.Student;

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

    @GET
    @Path("branch/{branch}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentListByBranch(@PathParam("branch")String branch){
        List<Student> studentList = studentRepository.list("branch",branch);
        return Response.ok(studentList).build();
    }

    @POST
    @RolesAllowed("admin")
    @Path("addStudent")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@RequestBody Student student) {
        System.out.println(student);
        studentRepository.persist(student);
        if (studentRepository.isPersistent(student)) {
            return Response.created(URI.create("/student/" + student.getStudentId())).build();
        } else {
            return Response.ok(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @RolesAllowed({"admin","teacher"})
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("id")Long id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            return Response.ok(Response.status(Response.Status.NO_CONTENT)).build();
        } else {
            return Response.ok(student).build();
        }
    }

    @GET
    @RolesAllowed({"admin","teacher","student"})
    @Path("getAllStudent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentList(){
        List<Student> studentList =studentRepository.listAll();
        return Response.ok(studentList).build();
    }

    @PUT
    @Path("update/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(@RequestBody Student studentUpdate,@PathParam("id")Long id) {
        Student student = studentRepository.findById(id);
        if (student!= null) {
            student.setName(studentUpdate.getName());
            return Response.ok(student).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    @DELETE
    @Path("delete/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteStudent(@PathParam("id")Long id) {
        boolean isDeleted = studentRepository.deleteById(id);
        if (isDeleted) {
            return Response.ok(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
