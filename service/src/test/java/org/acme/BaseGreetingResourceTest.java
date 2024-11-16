package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.apache.fury.Fury;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.acme.GreetingResource.BAR_CLASS_ID;
import static org.hamcrest.CoreMatchers.is;
import io.restassured.RestAssured;


public class BaseGreetingResourceTest {
    @Test
    public void testHelloEndpoint() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        given()
          .contentType("application/json")
          .body("{\"f2\":\"test\"}")
          .when().post("/test/json")
          .then()
             .statusCode(200)
             .body(is("Hello from Quarkus REST test"));
    }
    
    @Test
    public void testFuryBar() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        Bar bar = new Bar(1, "hello bar");
        Fury fury = Fury.builder().requireClassRegistration(true).withName("Fury" + System.nanoTime()).build();
        fury.register(Bar.class, BAR_CLASS_ID);
        fury.registerSerializer(Bar.class, BarSerializer.class);
        
        Response response = given().contentType("application/fury").body(fury.serialize(bar)).when()
                .post("/test/fury").then().statusCode(200).contentType("application/fury").extract().response();
        
        byte[] result = response.body().asByteArray();
        Bar bar2 = (Bar) fury.deserialize(result);
        Assertions.assertEquals(bar2.f1(), 2);
        Assertions.assertEquals(bar2.f2(), "bye bar");
    }
}
