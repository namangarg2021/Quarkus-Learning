package org.hprtech.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.vertx.ext.auth.authorization.Authorization;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
@Tag("integration")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentResourceTest3 {

    @Order(1)
    @TestSecurity(authorizationEnabled=false)
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
    @TestSecurity(user = "testUser",roles = "teacher")
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
    @TestSecurity(user = "testUser",roles = "admin")
    @Test
    void getStudentList() {
        RestAssured.given()
                .when()
                .get("getAllStudent")
                .then()
                .body("size()",equalTo(6));
    }
}