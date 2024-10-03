package ca.gbc.productservice;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        // Combine baseURI and port correctly to form the complete URL
        RestAssured.baseURI = "http://localhost:" + port;
    }

    static {
        mongoDBContainer.start();
    }

    @Test
    void createProductTest() {

        String requestBody = """
                {
                    "Name": "Samsung TV",
                    "Description": "Samsung TV - Model 2024",
                    "Price": 2000
                }
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/products")
                .then()
                .log().all()
                .statusCode(201)
                .body("Id", Matchers.notNullValue())
                .body("Name", Matchers.equalTo("Samsung TV"))
                .body("Description", Matchers.equalTo("Samsung TV - Model 2024"))
                .body("Price", Matchers.equalTo(2000));  // Update this to expect a numeric value
    }

    @Test
    void getProductTest() {
        String requestBody = """
                {
                    "Name": "Samsung TV",
                    "Description": "Samsung TV - Model 2024",
                    "Price": 2000
                }
                """;

        // Create the product first
        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/products")
                .then()
                .log().all()
                .statusCode(201)
                .body("Id", Matchers.notNullValue())
                .body("Name", Matchers.equalTo("Samsung TV"))
                .body("Description", Matchers.equalTo("Samsung TV - Model 2024"))
                .body("Price", Matchers.equalTo(2000)); // Expecting a numeric value

        // Now fetch the product
        RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/api/products")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", Matchers.greaterThan(0))  // Ensure that at least one product is returned
                .body("[0].Name", Matchers.equalTo("Samsung TV"))
                .body("[0].Description", Matchers.equalTo("Samsung TV - Model 2024"))
                .body("[0].Price", Matchers.equalTo(2000)); // Expecting a numeric value
    }
}