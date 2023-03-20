package apiTest;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class APIGetRequest {
    @Test
    public void test() {
        String url = "https://jsonplaceholder.typicode.com/posts/44";


        JSONObject expectedBody = new JSONObject();
        expectedBody.put("userId",5);
        expectedBody.put("title","optio dolor molestias sit" );
        System.out.println(expectedBody);

        Response response= given().when().get(url);
        response.prettyPrint();

        response.then().assertThat().statusCode(200).contentType(ContentType.JSON);
        JsonPath actualBody= response.jsonPath();
        System.out.println("actualBody = "+ actualBody.get("userId"));
        Assert.assertEquals(expectedBody.get("userId"),actualBody.get("userId"));
        Assert.assertEquals(expectedBody.get("title"),actualBody.get("title"));


    }
}
