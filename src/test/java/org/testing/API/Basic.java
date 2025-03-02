package org.testing.API;

import io.restassured.RestAssured;
import org.testing.API.payload.CreatePlace;

import static io.restassured.RestAssured.given;

public class Basic {


    public static void main(String[] args) {


        RestAssured.baseURI = "https://rahulshettyacademy.com";
        given().queryParam("key","qaclick123").headers("content-Type","application/json").body(CreatePlace.payload()).when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).log().all();

    }




}
