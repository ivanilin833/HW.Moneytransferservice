package com.example.moneytransfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConditionalAppTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Container
    private final GenericContainer<?> front = new GenericContainer<>("front_transfer_money:latest")
            .withExposedPorts(3000);
    @Container
    private final GenericContainer<?> back = new GenericContainer<>("back_transfer_money:latest")
            .withExposedPorts(5500);

    @Test
    void testIntegration() throws JsonProcessingException {
        //Arrange
        String requestBodyTransfer = "{\n" +
                "  \"cardToNumber\": \"2222334445223555\",\n" +
                "  \"cardFromNumber\": \"5555444323332222\",\n" +
                "  \"cardFromCVV\": \"222\",\n" +
                "  \"cardFromValidTill\": \"06/29\",\n" +
                "  \"amount\": {\n" +
                "    \"currency\": \"RUR\",\n" +
                "    \"value\": 500000\n" +
                "  }\n" +
                "}";
        //Act
        String responseBodyTransfer = getResponseBody(requestBodyTransfer, "/transfer");
        String requestBodyConfirm = "{\n" +
                "  \"code\": \"0000\",\n" +
                "  \"operationId\": \"" + responseBodyTransfer + "\"\n" +
                "}";
        String responseBodyConfirmOperation = getResponseBody(requestBodyConfirm, "/confirmOperation");
        //Assert
        Assertions.assertEquals(responseBodyTransfer, responseBodyConfirmOperation);
    }

    private String getResponseBody(String requestBodyTransfer, String endpoint) throws JsonProcessingException {
        String body = given()
                .baseUri(getUri(front.getHost(), front.getMappedPort(3000)))
                .body(requestBodyTransfer)
                .header("Content-Type", "application/json")
                .when()
                .post(getUri(back.getHost(), back.getMappedPort(5500)) + endpoint)
                .then()
                .statusCode(200)
                .extract().asString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(body);

        return jsonResponse.get("operationId").asText();
    }

    private String getUri(String host, int port) {
        return "http://" + host + ":" + port;
    }
}