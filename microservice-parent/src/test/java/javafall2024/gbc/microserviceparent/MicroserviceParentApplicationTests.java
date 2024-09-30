package javafall2024.gbc.microserviceparent;

import io.restassured.RestAssured;
import net.bytebuddy.asm.Advice;
import org.assertj.core.error.MatcherShouldMatch;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

// Tells spring boot to look for a main configuration class (@SpringBootApplication)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MicroserviceParentApplicationTests {

	// This annotation is used in combination with TestContainers to automatically configure the connection to
	// the Test MongoDBContainer
	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	@LocalServerPort()
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost:";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void createProductTest(){

		String requestBody = """
				{
					"Name" : "Samsung TV",
					"Description : "Samsung TV - Model 2024",
					"Price : "2000";
				}
				""";
		// BDD - 0 Behavioural Driven Development (Given, When, Then)
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/products")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("Name", Matchers.equalTo("Samsung TV"))
				.body("Description", Matchers.equalTo("Samsung TV - Model 2024"))
				.body("Price", Matchers.equalTo("2000"));

	}

	@Test
	void getAllProductTest(){
		String requestBody = """
				{
					"Name" : "Samsung TV",
					"Description : "Samsung TV - Model 2024",
					"Price : "2000";
				}
				""";
		// BDD - 0 Behavioural Driven Development (Given, When, Then)
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/products")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("Name", Matchers.equalTo("Samsung TV"))
				.body("Description", Matchers.equalTo("Samsung TV - Model 2024"))
				.body("Price", Matchers.equalTo("2000"));

		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/products")
				.then()
				.log().all()
				.statusCode(200)
				.body("size", Matchers.greaterThan(0))
				.body("[0].name", Matchers.equalTo("Samsung TV"))
				.body("Description", Matchers.equalTo("Samsung TV - Model 2024"))
				.body("Price", Matchers.equalTo("2000"));


	}

}
