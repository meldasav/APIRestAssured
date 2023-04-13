package day2;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class WaysToCreateRequestBody {

    // @Test(priority = 1)
    public void testPostUsingHashMap() {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "john");
        requestBody.put("location", "USA");
        requestBody.put("phone", "12345678");

        String[] course = {"Java", "Selenium"};
        requestBody.put("courses", course);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post(" http://localhost:3000/students")
                .then().statusCode(201)
                .body("name", equalTo("john"))
                .body("location", equalTo("USA"))
                .body("phone", equalTo("12345678"))
                .body("courses[0]", equalTo("Java"))
                .header("Content-Type", "application/json; charset=utf-8")
                .log().all();
    }

    @Test(priority = 2)
    public void testDelete() {
        when().delete(" http://localhost:3000/students/5")
                .then().statusCode(200);
    }

    // @Test(priority = 3)
    public void testPostUsingJsonLibrary() {
        JSONObject requestBody = new JSONObject();//with the json library , you need to use to string method when you past the body
        requestBody.put("name", "john");
        requestBody.put("location", "USA");
        requestBody.put("phone", "12345678");
        String[] course = {"Java", "Selenium"};
        requestBody.put("courses", course);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when().post("http://localhost:3000/students")
                .then().statusCode(201)
                .body("name", equalTo("john"))
                .body("location", equalTo("USA"))
                .body("phone", equalTo("12345678"))
                .body("courses[0]", equalTo("Java"))
                .header("Content-Type", "application/json; charset=utf-8")
                .log().all();
    }

//    @Test(priority = 4)
    public void TestPostUsingPojo() {
       ArrayList<String> course = new ArrayList<>(Arrays.asList("Java", "Selenium"));
        Pojo_Post_Request pojoPostRequest = Pojo_Post_Request.builder()
                .name("john")
                .location("USA")
                .phone("12345678")
                .courses((course))
                .build();

                 given()
                .contentType(ContentType.JSON)
                .body(pojoPostRequest)
                .when().post("http://localhost:3000/students")
                .then().statusCode(201)
                .body("name", equalTo("john"))
                .body("location", equalTo("USA"))
                .body("phone", equalTo("12345678"))
                .body("courses[0]", equalTo("Java"))
                .header("Content-Type", "application/json; charset=utf-8")
                .log().all();
    }

     @Test(priority = 5)
    public void testPostUsingExternalJsonFile() throws FileNotFoundException {

        File file = new File("requestBody.json");
         FileReader fileReader =new FileReader(file);
         JSONTokener jsonTokener = new JSONTokener(fileReader);
         JSONObject requestBody = new JSONObject(jsonTokener);

         given()
                 .contentType(ContentType.JSON)
                 .body(requestBody.toString())
                 .when().post("http://localhost:3000/students")
                 .then().statusCode(201)
                 .body("name", equalTo("john"))
                 .body("location", equalTo("USA"))
                 .body("phone", equalTo("12345678"))
                 .body("courses[0]", equalTo("Java"))
                 .header("Content-Type", "application/json; charset=utf-8")
                 .log().all();
     }
     }


