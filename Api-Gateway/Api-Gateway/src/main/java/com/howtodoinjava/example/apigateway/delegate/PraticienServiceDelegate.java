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

@Service
public class PraticienServiceDelegate {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = System.getenv("PRATICIEN_SERVICE_URI") != null
            ? System.getenv("PRATICIEN_SERVICE_URI") + "/swagger2-praticien-service/api/praticien"
            : "http://localhost:8012/swagger2-praticien-service/api/praticien";


    @HystrixCommand(fallbackMethod = "fallbackGetPraticienDetails")
    public JsonNode getPraticienDetails(int praticienId) {
        String url = BASE_URL + "/" + praticienId;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackAddPraticien")
    public JsonNode addPraticien(JsonNode praticien) {
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(praticien),
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackUpdatePraticien")
    public JsonNode updatePraticien(int praticienId, JsonNode updatedPraticien) {
        String url = BASE_URL + "/" + praticienId;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(updatedPraticien),
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackDeletePraticien")
    public JsonNode deletePraticien(int praticienId) {
        String url = BASE_URL + "/" + praticienId;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackGetAllPraticiens")
    public JsonNode getAllPraticiens() {
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public JsonNode fallbackGetPraticienDetails(int praticienId) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Service is currently unavailable")
                .put("praticienId", praticienId);
    }

    public JsonNode fallbackAddPraticien(JsonNode praticien) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode().put("message", "Unable to add praticien at the moment");
    }

    public JsonNode fallbackUpdatePraticien(int praticienId, JsonNode updatedPraticien) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Unable to update praticien at the moment")
                .put("praticienId", praticienId);
    }

    public JsonNode fallbackDeletePraticien(int praticienId) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Unable to delete praticien at the moment")
                .put("praticienId", praticienId);
    }

    public JsonNode fallbackGetAllPraticiens() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode().put("message", "Service is currently unavailable");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
