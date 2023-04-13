package day1;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class HTTPRequest {

    int id;

    @Test(priority = 1)
    public void getUsers() {

        when().get("https://reqres.in/api/users?page=2")
                .then().statusCode(200)
                .body("page", equalTo(2))
                .log().all();
    }

    @Test(priority = 2)
    public void createUser() {
        HashMap<String, String> request = new HashMap<>();
        request.put("name", "Melda");
        request.put("job", "tester");

        id = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("https://reqres.in/api/users")
                .jsonPath().getInt("id");
    }

    @Test(priority = 3)
    public void updateUser() {
        HashMap<String, String> request = new HashMap<>();
        request.put("name", "Melda");
        request.put("job", "SDET");

                 given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().put("https://reqres.in/api/users/" + id)
                .then().statusCode(200)
                .log().all();
    }

    @Test(priority = 4)
    public void deleteUser(){
        when().delete("https://reqres.in/api/users/"+ id)
                .then().statusCode(204)
                .log().all();
    }
}
