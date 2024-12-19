package com.howtodoinjava.example.patient.delegate;

import com.fasterxml.jackson.databind.JsonNode;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PatientServiceDelegate {

    @Autowired
    private RestTemplate restTemplate;

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
        // Return a default JSON response in case of failure
        return new RestTemplate().getForObject("data:application/json,{\"message\":\"Service is currently unavailable\",\"patientId\":" + patientId + "}", JsonNode.class);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
