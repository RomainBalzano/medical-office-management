package com.howtodoinjava.example.praticien.delegate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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




    private static final String BASE_URL = System.getenv("EUREKA_SERVER_URI") != null
            ? System.getenv("EUREKA_SERVER_URI").replace("/eureka/", "") + "/swagger2-dossiermedical-service/api/dossierMedical/"
            : "http://localhost:8014/swagger2-dossiermedical-service/api/dossierMedical/";

    @HystrixCommand(fallbackMethod = "fallbackGetDossierMedical")
    public JsonNode getDossierMedicalByPatientId(int patientId) {
        String url = "http://localhost:8014/swagger2-dossiermedical-service/api/dossierMedical/" + patientId;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }


    public JsonNode fallbackGetDossierMedical(int patientId) {
        System.out.println("Fallback triggered for patientId: " + patientId);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode fallbackResponse = objectMapper.createObjectNode();
        fallbackResponse.put("message", "Service is currently unavailable");
        fallbackResponse.put("patientId", patientId);

        return fallbackResponse;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
