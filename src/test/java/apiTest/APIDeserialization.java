package apiTest;

import baseUrl.JsonPlaceholderBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;
import testData.JsonPlaceholderTestData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class APIDeserialization extends JsonPlaceholderBaseUrl {
    JsonPlaceholderTestData jsonPlaceholderTestData = new JsonPlaceholderTestData();

    @Test
    public void test1() {
        specJsonPlace.pathParam("pp1", 70);
        Map<String, Object> reqBodyMap = jsonPlaceholderTestData.requestBodyMethodMap();
        System.out.println(reqBodyMap);

        Map<String, Object> expBodyMap = jsonPlaceholderTestData.requestBodyMethodMap();

        Response response = given().contentType(ContentType.JSON)
                .spec(specJsonPlace).
                when().body(reqBodyMap).put("{pp1}");

        response.prettyPrint();

        //gson dependency aracılığı ile json mape dönüştü
        Map<String, Object> respMap = response.as(HashMap.class);
        System.out.println(respMap);

        response.then().assertThat().statusCode(jsonPlaceholderTestData.codeOK).contentType(ContentType.JSON);
        assertEquals(jsonPlaceholderTestData.codeOK, response.getStatusCode());
        assertEquals(expBodyMap.get("userId"), respMap.get("userId"));
        assertEquals(expBodyMap.get("id"), respMap.get("id"));
        assertEquals(expBodyMap.get("title"), respMap.get("title"));
        assertEquals(expBodyMap.get("body"), respMap.get("body"));


    }

}
