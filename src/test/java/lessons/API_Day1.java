package lessons;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

public class API_Day1 {
    @Before
    public void setUp() {

    }

    @Test
    public void getRandomActivityAndPrint() {
        Response response = given().contentType(ContentType.JSON).
                get("http://www.boredapi.com/api/activity");
        response.prettyPrint();
    }

    @Test
    public void number9008639BringsMemorizeActivity1() {

        Response response = given().contentType(ContentType.JSON).
                get("http://www.boredapi.com/api/activity?key=9008639");
        //response.prettyPrint();
        // System.out.println(response.body().print());
        String expected = "{\"activity\":\"Memorize a favorite quote or poem\",\"type\":\"education\",\"participants\":1,\"price\":0,\"link\":\"\",\"key\":\"9008639\",\"accessibility\":0.8}";
        assertEquals(expected, response.body().print());
    }

    @Test
    public void number9008639BringsMemorizeActivity2() {
        //queryparam ile yapılınca
        Response response = given().contentType(ContentType.JSON).queryParam("key", 9008639).
                get("http://www.boredapi.com/api/activity");
        System.out.println(response.asString());
        System.out.println("****");
        String expected = "{\"activity\":\"Memorize a favorite quote or poem\",\"type\":\"education\",\"participants\":1,\"price\":0,\"link\":\"\",\"key\":\"9008639\",\"accessibility\":0.8}";
        assertEquals(expected, response.body().print());
    }

    @Test
    public void number3BringsAstonCox(){
        //pathparam ekleme
        Response response = given().contentType(ContentType.JSON).pathParam("number",3).
                get("https://dummy.restapiexample.com/api/v1/employee/{number}");
        System.out.println("********************************");
        response.prettyPrint();
        System.out.println("!!!!!!!!");
        response.print();
        System.out.println("********************************");

         System.out.println(response.body().print());
        System.out.println("++++++++++++ ");
        String expected = "{\n" +
                "    \"status\": \"success\",\n" +
                "    \"data\": {\n" +
                "        \"id\": 3,\n" +
                "        \"employee_name\": \"Ashton Cox\",\n" +
                "        \"employee_salary\": 86000,\n" +
                "        \"employee_age\": 66,\n" +
                "        \"profile_image\": \"\"\n" +
                "    },\n" +
                "    \"message\": \"Successfully! Record has been fetched.\"\n}";
        assertEquals(200, response.getStatusCode());
        assertEquals(expected, response.body().print());

    }
    @Test
    public void test(){

    }

}