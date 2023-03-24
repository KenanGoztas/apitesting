package lessons;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class API_Day2 {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void makeBasicGetRequestAndAssert() {
        RestAssured.baseURI = "http://restcountries.com/v3.1/name";
        Response response = RestAssured.get("turkey");
        response.print();
        assertEquals(200, response.getStatusCode());
        assertEquals("application/json", response.contentType());
        boolean doesDataExist = response.getHeaders().hasHeaderWithName("Date");
        if (doesDataExist) {
            System.out.println(response.getHeader("Date"));
            Assert.assertTrue(response.getHeader("Date").contains("2023"));
        }
    }

    @Test
    public void randomNumvertest() {
        RestAssured.baseURI = "http://numbersapi.com/random";
        Response response = RestAssured.get("math");
        response.print();
        assertEquals(200, response.getStatusCode());
        assertEquals("text/plain; charset=utf-8", response.getContentType());
        Assert.assertTrue(response.getHeader("date").contains("2023"));
        assertEquals("no-cache", response.getHeader("pragma"));
    }

    @Test
    public void apiLimitCounterDecreasesWhenARequestIsMade() {
        String url = "http://dummy.restapiexample.com/api/v1/employees";
        Response response = RestAssured.get(url);
        assertEquals("60", response.getHeader("X-RateLimit-Limit"));
        int initialLimit = Integer.parseInt(response.getHeader("X-RateLimit-Remaining"));
        String updatedLimit = RestAssured.get(url).getHeader("X-RateLimit-Remaining");

        assertEquals((initialLimit) - 1, Integer.parseInt(updatedLimit));
    }

    @Test
    public void turkeyHasBordersWithEightCountries() {
        //birinci yöntem
        String country = "turkey";
        Response response = RestAssured.get("http://restcountries.com/v3.1/name/" + country);

        //ikinci yöntem
        Response response1 = RestAssured.given().pathParam("country_name", "turkey").get("http://restcountries.com/v3.1/name/{country_name}");

        assertEquals(200, response1.getStatusCode());

        String response2Body = response.asString();
        System.out.println(response2Body);
        assertTrue(response2Body.contains("ARM"));

    }

    @Test
    public void useRestAssurePathMethodToReadData() {
        //response.path(key) metodu json datasının içindeki veriyi getiriyor
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        Response response = RestAssured.given().pathParam("com", "90").get("comments/{com}");
        assertEquals(200, response.getStatusCode());
        System.out.println("" + response.path("name"));
        System.out.println("" + response.path("id"));
        System.out.println("" + response.path("body"));

    }

    @Test
    public void usePathMethodForComplexJsonStructure() {
        //json datasının içiçe verilere nasıl ulaşabiliriz
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        Response response = RestAssured.given().pathParam("com", "8").get("users/{com}");
        assertEquals(200, response.getStatusCode());
        response.prettyPrint();
        assertEquals("Abernathy Group", response.path("company.name"));
        assertEquals("-120.7677", response.path("address.geo.lng"));
    }

    @Test
    public void usePathMethodForArrayJson() {
        //json datasının içindeki arraya ulaşmak..
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        //Response response= RestAssured.given().pathParam("com", "todos").get("{com}");
        Response response = RestAssured.get("todos");
        assertEquals(200, response.getStatusCode());
        //response.prettyPrint();
        System.out.println("" + response.path("[0].id"));

        assertEquals("4", "" + response.path("[3].id"));
        assertEquals(1, (int) response.path("[3].userId"));
        assertEquals(true, (boolean) response.path("[3].completed"));
    }

    @Test
    public void useGerkhinMethodsAndBuildInAssertion() {
        RestAssured.baseURI = "http://restcountries.com/v3.1/name";
        //Response response= RestAssured.get("turkey");
        RestAssured.given().pathParam("country", "turkey")
                .baseUri("http://restcountries.com/v3.1")
                .when().contentType("application/json")
                .and().get("name/{country}")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .and().assertThat().header("Server", "Apache/2.4.38 (Debian)");
    }

    @Test
    public void useMachersLibraryForAssertions() {
        //RestAssured.baseURI="http://restcountries.com/v3.1/name";
        //Response response= RestAssured.get("turkey");
        RestAssured.given().pathParam("country", "turkey")
                .and().baseUri("http://restcountries.com/v3.1")
                .when().contentType("application/json")
                .and().get("name/{country}")
                .then().assertThat().statusCode(200)
                .assertThat().statusCode(Matchers.instanceOf(Integer.class))
                .assertThat().statusCode(Matchers.greaterThan(199))
                .assertThat().statusCode(Matchers.lessThan(201))
                .assertThat().statusCode(Matchers.anyOf(Matchers.equalTo(200), Matchers.equalTo(201)))
                .assertThat().contentType(Matchers.instanceOf(String.class))
                .assertThat().contentType(Matchers.notNullValue())
                .assertThat().header("Server", Matchers.not(Matchers.emptyOrNullString()))
                .assertThat().header("Server", Matchers.containsString("Debian"));
    }

    @Test
    public void useRestAssuredLogging() {
        RestAssured.given().pathParam("country", "turkey").log().uri()
                .and().baseUri("http://restcountries.com/v3.1")
                .when().contentType("application/json").log().body().log().method()
                .and().get("name/{country}")
                .then()//.statusCode(Matchers.is(200))
                .log().ifError();

        RestAssured.given().pathParam("country", "turkey")
                .and().baseUri("http://restcountries.com/v3.1")
                .when().contentType("application/json")
                .and().get("name/{country}")
                .then().log().everything();
    }

    @Test
    public void deserializateToMap() {
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get("http://randomuser.me/api")
                .then().assertThat().statusCode(200).extract().response();

        Map<String, Object> deSerializatedObject = response.body().as(Map.class);
        System.out.println(deSerializatedObject.toString());
    }

    @Test
    public void deserializateToList() {
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get("http://restcountries.com/v3.1/name/turkey")
                .then().assertThat().statusCode(200).extract().response();

        System.out.println((List<String>) response.path("[0].borders"));
        List<String> list= response.path("[0].borders");
        System.out.println(list.get(1));

    }


}
