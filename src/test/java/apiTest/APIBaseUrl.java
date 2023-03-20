package apiTest;

import baseUrl.JsonPlaceholderBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import testData.JsonPlaceholderTestData;

import static io.restassured.RestAssured.given;

public class APIBaseUrl extends JsonPlaceholderBaseUrl {
    @Test
    public void test1(){
        JsonPlaceholderTestData jsonPlaceholderTestData=new JsonPlaceholderTestData();
       JSONObject expBody= jsonPlaceholderTestData.expectedDataJson();
        //expected
//        JSONObject expectedBody = new JSONObject();
//        expectedBody.put("userId",3);
//        expectedBody.put("id",22);
//        expectedBody.put("title","dolor sint quo a velit explicabo quia nam" );
//        //expectedBody.put("eos qui et ipsum ipsam suscipit aut\nsed omnis non odio\nexpedita earum mollitia molestiae aut atque rem suscipit\nnam impedit esse");
//        System.out.println(expectedBody);

        specJsonPlace.pathParam("pp1",22);
        Response response = given().spec(specJsonPlace).when().get("{pp1}");
        response.prettyPrint();

        response.then().assertThat().statusCode(jsonPlaceholderTestData.codeOK).contentType(ContentType.JSON);

        JsonPath actualBody= response.jsonPath();
        Assert.assertEquals(jsonPlaceholderTestData.codeOK, response.getStatusCode());
        Assert.assertEquals(expBody.get("userId"),actualBody.get("userId"));
        Assert.assertEquals(expBody.get("id"),actualBody.get("id"));
        Assert.assertEquals(expBody.get("title"),actualBody.get("title"));
        Assert.assertEquals(expBody.get("body"),actualBody.get("body"));

    }
}
