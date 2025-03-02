package org.testing.API;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testing.API.payload.CreatePlace;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basic {


    public static void main(String[] args) {


        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().queryParam("key", "qaclick123").headers("content-Type", "application/json").body(CreatePlace.payload())
                .when().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).headers("server", "Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();

        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");

        String newAddress = "70 Ananda Mawatha, USA";

        given().queryParam("key", "qaclick123").headers("content-Type", "application/json").body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n").when().put("/maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        given().queryParam("key", "qaclick123").queryParam("place_id", placeId).when().get("/maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).body("address", equalTo(newAddress));

    }


}
