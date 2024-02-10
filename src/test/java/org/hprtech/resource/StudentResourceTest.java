package org.hprtech.resource;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import static org.hamcrest.CoreMatchers.*;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;

@QuarkusTest
@Tag("integration")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentResourceTest {

//    @Test
//    void getStudentList() {
//        RestAssured.given()
//                    .when()
//                    .get("/getStudentList")
//                    .then()
//                    .body("size()", CoreMatchers.equalTo(4))
//                    .body("name",hasItems("Rahul","Mohit"))
//                    .body("branch",hasItems("CS","EE"));
//    }
//
//    @Test
//    void getCsStudentList() {
//        RestAssured.given()
//                .when()
//                .get("/getCsStudentList")
//                .then()
//                .body("size()", CoreMatchers.equalTo(2))
//                .body("name",hasItems("Rahul","Aakanksha"));
//    }

    @Order(1)
    @Transactional
    @Test
    void addStudent() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("name","Elon Musk")
                .add("branch","CS")
                .build();
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/addStudent")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

    }
    @Order(2)
    @Test
    void getStudentById() {
        RestAssured.given()
                .when()
                .get("student/10")
                .then()
                .body("StudentId",equalTo(10))
                .body("name",equalTo("Elon Musk"))
                .body("branch",equalTo("CS"))
                .statusCode(Response.Status.CREATED.getStatusCode());

    }

    @Order(3)
    @Test
    void getStudentList() {
        RestAssured.given()
                .when()
                .get("getAllStudent")
                .then()
                .body("size()",equalTo(6));
    }
}