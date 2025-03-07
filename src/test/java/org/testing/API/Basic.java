package org.testing.API;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testing.API.payload.CreatePlace;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basic {


    public static void main(String[] args) {

        RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .addHeader("content-Type", "application/json").build();
        ResponseSpecification resSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200).
                expectHeader("server", "Apache/2.4.52 (Ubuntu)").build();

        RequestSpecification requestSpecification = given().spec(reqSpec).body(CreatePlace.payload());
        String response = requestSpecification.when().post("/maps/api/place/add/json")
                .then().spec(resSpec).log().all()
                .extract().response().asString();

        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");

        String newAddress = "70 Ananda Mawatha, USA";

        given().spec(reqSpec).body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("/maps/api/place/update/json")
                .then().spec(resSpec).log().all().body("msg", equalTo("Address successfully updated"));

        given().spec(reqSpec).queryParam("place_id", placeId)
                .when().get("/maps/api/place/get/json")
                .then().log().all().spec(resSpec).body("address", equalTo(newAddress));

    }
}
