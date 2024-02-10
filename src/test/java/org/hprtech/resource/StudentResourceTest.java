package org.hprtech.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import static org.hamcrest.CoreMatchers.*;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@Tag("integration")
class StudentResourceTest {

    @Test
    void getStudentList() {
        RestAssured.given()
                    .when()
                    .get("/getStudentList")
                    .then()
                    .body("size()", CoreMatchers.equalTo(4))
                    .body("name",hasItems("Rahul","Mohit"))
                    .body("branch",hasItems("CS","EE"));
    }

    @Test
    void getCsStudentList() {
        RestAssured.given()
                .when()
                .get("/getCsStudentList")
                .then()
                .body("size()", CoreMatchers.equalTo(2))
                .body("name",hasItems("Rahul","Aakanksha"));
    }
}