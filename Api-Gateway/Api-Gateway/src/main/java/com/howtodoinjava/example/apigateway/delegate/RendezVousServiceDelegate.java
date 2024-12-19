package com.howtodoinjava.example.apigateway.delegate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RendezVousServiceDelegate {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://rendezvous-service/api/rendezVouss";

    @HystrixCommand(fallbackMethod = "fallbackGetAllRendezVouss")
    public JsonNode getAllRendezVouss() {
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackGetRendezVousDetails")
    public JsonNode getRendezVousDetails(int rendezVousId) {
        String url = BASE_URL + "/" + rendezVousId;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackAddRendezVous")
    public JsonNode addRendezVous(JsonNode rendezVous) {
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(rendezVous),
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackUpdateRendezVous")
    public JsonNode updateRendezVous(int rendezVousId, JsonNode updatedRendezVous) {
        String url = BASE_URL + "/" + rendezVousId;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(updatedRendezVous),
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackDeleteRendezVous")
    public JsonNode deleteRendezVous(int rendezVousId) {
        String url = BASE_URL + "/" + rendezVousId;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public JsonNode fallbackGetAllRendezVouss() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode().put("message", "Service is currently unavailable");
    }

    public JsonNode fallbackGetRendezVousDetails(int rendezVousId) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Service is currently unavailable")
                .put("rendezVousId", rendezVousId);
    }

    public JsonNode fallbackAddRendezVous(JsonNode rendezVous) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode().put("message", "Unable to add rendezVous at the moment");
    }

    public JsonNode fallbackUpdateRendezVous(int rendezVousId, JsonNode updatedRendezVous) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Unable to update rendezVous at the moment")
                .put("rendezVousId", rendezVousId);
    }

    public JsonNode fallbackDeleteRendezVous(int rendezVousId) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Unable to delete rendezVous at the moment")
                .put("rendezVousId", rendezVousId);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
