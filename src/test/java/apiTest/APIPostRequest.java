package apiTest;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class APIPostRequest {
    @Test
    public void test() {
        String url = "https://jsonplaceholder.typicode.com/posts";

        JSONObject reqBody = new JSONObject();
        reqBody.put("title", "API");
        reqBody.put("body", "API ogrenmek ne guzel");
        reqBody.put("userId", 10);

        //Expected
        JSONObject exBody = new JSONObject();
        exBody.put("title", "API");
        exBody.put("body", "API ogrenmek ne guzel");
        exBody.put("userId", 10);

        //response kaydı
        Response response = given().contentType(ContentType.JSON).body(reqBody.toString()).post(url);

        JsonPath actBody = response.jsonPath();
        //assertion

        response.then().assertThat().contentType(ContentType.JSON).statusCode(201);

        Assert.assertEquals(exBody.get("title"), actBody.get("title"));
        Assert.assertEquals(exBody.get("body"), actBody.get("body"));
        Assert.assertEquals(exBody.get("userId"), actBody.get("userId"));


    }
}
