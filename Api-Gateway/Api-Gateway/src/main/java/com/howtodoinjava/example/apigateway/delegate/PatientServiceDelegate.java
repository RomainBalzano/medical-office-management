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
public class PatientServiceDelegate {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://patient-service/api/patients";

    @HystrixCommand(fallbackMethod = "fallbackGetPatientDetails")
    public JsonNode getPatientDetails(int patientId) {
        String url = BASE_URL + "/" + patientId;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackAddPatient")
    public JsonNode addPatient(JsonNode patient) {
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(patient),
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackUpdatePatient")
    public JsonNode updatePatient(int patientId, JsonNode updatedPatient) {
        String url = BASE_URL + "/" + patientId;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(updatedPatient),
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackDeletePatient")
    public JsonNode deletePatient(int patientId) {
        String url = BASE_URL + "/" + patientId;
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackGetAllPatients")
    public JsonNode getAllPatients() {
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallbackGetDossierMedicalByPatientId")
    public JsonNode getDossierMedicalByPatientId(int patientId) {
        String url = BASE_URL + "/" + patientId + "/dossier-medical";
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public JsonNode fallbackGetPatientDetails(int patientId) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Service is currently unavailable")
                .put("patientId", patientId);
    }

    public JsonNode fallbackAddPatient(JsonNode patient) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Unable to add patient at the moment");
    }

    public JsonNode fallbackUpdatePatient(int patientId, JsonNode updatedPatient) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Unable to update patient at the moment")
                .put("patientId", patientId);
    }

    public JsonNode fallbackDeletePatient(int patientId) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Unable to delete patient at the moment")
                .put("patientId", patientId);
    }

    public JsonNode fallbackGetAllPatients() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode().put("message", "Service is currently unavailable");
    }

    public JsonNode fallbackGetDossierMedicalByPatientId(int patientId) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("message", "Service is currently unavailable")
                .put("patientId", patientId);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
