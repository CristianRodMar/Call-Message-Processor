package com.cristian.callmessageprocessor.steps;

import static org.junit.Assert.assertEquals;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.cristian.callmessageprocessor.CucumberSpringConfiguration;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ControllerSteps extends CucumberSpringConfiguration {
    
    private String date;
    private ResponseEntity<Object> response;

    @Given("a date {string}")
    public void a_date(String date) {
        this.date = date;
    }

    @Given("reset values on memory")
    public void reset_Values() {
        String baseUrl = "http://localhost:8080/reset";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Object.class);
    }

    @When("the user makes a request to {string}")
    public void make_a_request(String route) {
        try {
            String baseUrl = "http://localhost:8080/" + date + route;
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, Object.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsByteArray(), e.getStatusCode());
        }
    }

    @When("the user makes a general request to {string}")
    public void general_request_to(String route) {
        try {
            String baseUrl = "http://localhost:8080/" + route;
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, Object.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsByteArray(), e.getStatusCode());
        }
    }

    @Then("the user should receive a code {int} response")
    public void receive_a_code(int expectedStatusCode) {
        HttpStatusCode statusCode = response.getStatusCode();
        assertEquals(expectedStatusCode, statusCode.value());
    }       

}
