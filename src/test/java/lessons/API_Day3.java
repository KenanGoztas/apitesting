package lessons;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class API_Day3 {
    @Test
    public void makeJsonSchemaValidationForBody1(){
        Response response= RestAssured
                .given().baseUri("http://boredapi.com/api")
                .when().accept(ContentType.JSON)
                .and().get("activity")
                .then().assertThat().contentType(ContentType.JSON)
                .and().assertThat().statusCode(200).log().body()
                .and().assertThat().body("activity", Matchers.instanceOf(String.class))
                .and().assertThat().body("type", Matchers.instanceOf(String.class))
                .and().assertThat().body("participants", Matchers.instanceOf(Integer.class))
                .and().assertThat().body("price", Matchers.anyOf(Matchers.instanceOf(Integer.class), Matchers.instanceOf(Float.class)))
                .extract().response();
    }

    @Test
    public void makeJsonSchemaValidationForBody2(){
        Response response= RestAssured
                .given().baseUri("http://boredapi.com/api")
                .when().accept(ContentType.JSON)
                .and().get("activity")
                .then().assertThat().contentType(ContentType.JSON)
                .and().assertThat().statusCode(200).log().body()
                .and().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("activity_schema.json"))
                .extract().response();

    }
    @Test
    public void makeJsonSchemaValidationForBody3(){
        int i= new Random().nextInt(501);
        Response response= RestAssured
                .given().baseUri("https://jsonplaceholder.typicode.com")
                .pathParam("randomNumber", i)
                .when().accept(ContentType.JSON)
                .and().get("comments/{randomNumber}")
                .then().assertThat().contentType(ContentType.JSON)
                .and().assertThat().statusCode(200).log().body()
                .and().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonplaceholder.json"))
                .extract().response();
    }

    @Test
    public void getBooking900(){
        Response response= RestAssured
                .given().accept(ContentType.ANY)
                .baseUri("https://restful-booker.herokuapp.com")
                .and().log().uri().log().method()
                .and().pathParam("bookingId", "90")
                .when().get("/booking/{bookingId}")
                .then().assertThat().statusCode(200)
                .extract().response();
    }
    public Response getBooking(int id){
        return  RestAssured
                .given().accept(ContentType.ANY)
                .baseUri("https://restful-booker.herokuapp.com")
                .and().log().uri().log().method()
                .and().pathParam("bookingId", id)
                .when().get("/booking/{bookingId}")
                .then().assertThat().statusCode(200).log().body()
                .extract().response();
    }
    @Test
    public void test(){
        Response response= getBooking(90);
        System.out.println(""+response.path("price"));
    }

    @Test
    public void getABookingAndDeserializeToJavaObject(){
        Map<String, Object> javaObj =getBooking(90).body().as(Map.class);
        System.out.println(javaObj.get("firstname"));
        assertEquals("Josh", javaObj.get("firstname"));
        assertEquals("Allen", javaObj.get("lastname"));
        assertEquals(true, javaObj.get("depositpaid"));

        Map<String, Date> bookingdates= (Map<String, Date>) javaObj.get("bookingdates");
        System.out.println(bookingdates.get("checkin"));

        Gson gson= new Gson();
        String serializedJson= gson.toJson(javaObj);
        System.out.println(serializedJson);

    }
}
