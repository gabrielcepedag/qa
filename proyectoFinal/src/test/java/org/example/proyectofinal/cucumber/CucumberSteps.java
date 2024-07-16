package org.example.proyectofinal.cucumber;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.example.proyectofinal.ProyectoFinalApplication;
import org.example.proyectofinal.dto.request.AuthRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = ProyectoFinalApplication.class)
@ActiveProfiles("dev")
public class CucumberSteps {

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    private ResponseEntity<String> response;


    @When("the client calls landing page endpoint")
    public void landingPageEndpointCall() {
        try {
            response = restTemplate.getForEntity("http://localhost:8080", String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @When("the client calls endpoint {string}")
    public void callToEndpoint(String endpoint) {
        try {
            response = restTemplate.getForEntity("http://localhost:8080" + endpoint, String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @When("the client calls {string} endpoint {string} with username {string} and password {string}")
    public void callToLoginEndpoint(String method, String endpoint, String username, String password) {
        String url = "http://localhost:8080" + endpoint;
        AuthRequestDTO authRequestDTO = AuthRequestDTO.builder().username(username).password(password).build();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<AuthRequestDTO> request = new HttpEntity<>(authRequestDTO, headers);

        try {
            response = restTemplate.exchange(url, getHttpMethod(method), request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getStatusCode());
        }
    }

    //==================    Status Code in response validation  ==================
    @Then("the client receives status code of {int}")
    public void parseReceivedStatusCode(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }

    //==================    Body in response validation     ==================
    @Then("the client receives HTML page as response")
    public void parseReceivedHtmlPage() {
        assertTrue(response.getHeaders().getContentType().toString().contains("text/html"));
    }

    @Then("the client receives message {string}")
    public void parseReceivedMessage(String errorMessage) throws Exception {
        JsonNode responseBody = objectMapper.readTree(response.getBody());
        String message = responseBody.has("message") ? responseBody.path("message").asText() : responseBody.path("error").asText();
        assertEquals(errorMessage, message);
    }

    //==================    Utils    ==================
    public HttpMethod getHttpMethod(String method){
        return switch (method.toUpperCase()) {
            case "GET" -> HttpMethod.GET;
            case "POST" -> HttpMethod.POST;
            case "PUT" -> HttpMethod.PUT;
            case "DELETE" -> HttpMethod.DELETE;
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        };
    }
}
