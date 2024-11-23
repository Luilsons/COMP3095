package ca.gbc.orderservice.stub;

import static com.github.tomakehurst.wiremock.client.WireMock.*; // Import WireMock static methods
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class InventoryClientStub {

    public static void stubInventoryCall(String skuCode, Integer quantity) {
        // Set up the stub to mock the response from the inventory service
        stubFor(get(urlEqualTo("/api/inventory?skuCode=" + skuCode + "&quantity=" + quantity)) // Define the URL to match
                .willReturn(aResponse()
                        .withStatus(200) // Set the status code for a successful response
                        .withHeader("Content-Type", "application/json") // Set the response content type as JSON
                        .withBody("true"))); // Mock the response body to return "true", indicating item is in stock
    }
}
