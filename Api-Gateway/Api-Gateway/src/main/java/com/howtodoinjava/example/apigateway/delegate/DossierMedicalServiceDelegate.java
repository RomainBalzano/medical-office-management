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
public class DossierMedicalServiceDelegate {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://dossier-medical-service/api/dossierMedical";

    @HystrixCommand(fallbackMethod = "fallbackGetDossierMedical")
    public JsonNode getDossierMedical(int idPatient) {
        String url = BASE_URL + "/" + idPatient;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackAddRendezVousToDossier")
    public JsonNode addRendezVousToDossier(int idPatient, List<Integer> rendezVousIds) {
        String url = BASE_URL + "/" + idPatient + "/rendezVous";
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(rendezVousIds),
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackUpdateDossierMedical")
    public JsonNode updateDossierMedical(int idPatient, JsonNode updatedDossierMedical) {
        String url = BASE_URL + "/" + idPatient;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(updatedDossierMedical),
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackGetAllDossiersMedicaux")
    public JsonNode getAllDossiersMedicaux() {
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackDeleteDossierMedical")
    public JsonNode deleteDossierMedical(int idPatient) {
        String url = BASE_URL + "/" + idPatient;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    // Fallback methods
    public JsonNode fallbackGetDossierMedical(int idPatient) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Service is currently unavailable")
                .put("idPatient", idPatient);
    }

    public JsonNode fallbackAddRendezVousToDossier(int idPatient, List<Integer> rendezVousIds) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Unable to add rendez-vous to dossier at the moment")
                .put("idPatient", idPatient);
    }

    public JsonNode fallbackUpdateDossierMedical(int idPatient, JsonNode updatedDossierMedical) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Unable to update dossier medical at the moment")
                .put("idPatient", idPatient);
    }

    public JsonNode fallbackGetAllDossiersMedicaux() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Service is currently unavailable");
    }

    public JsonNode fallbackDeleteDossierMedical(int idPatient) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Unable to delete dossier medical at the moment")
                .put("idPatient", idPatient);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
