package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

@QuarkusTest
public class EventResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/party/event")
          .then()
             .statusCode(200)
                .and().body(hasItems());
                //.body(is("Hello RESTEasy"));
    }

}