package com.howtodoinjava.example.apigateway.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.howtodoinjava.example.apigateway.delegate.PatientServiceDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("API Gateway for managing patient-related operations")
@RestController
@RequestMapping("/api/gateway/patients")
public class PatientController {

    @Autowired
    private PatientServiceDelegate patientServiceDelegate;

    @ApiOperation(value = "Get details of a patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patient details retrieved successfully"),
            @ApiResponse(code = 404, message = "Patient not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{patientId}")
    public ResponseEntity<JsonNode> getPatientDetails(@PathVariable int patientId) {
        JsonNode response = patientServiceDelegate.getPatientDetails(patientId);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Add a new patient")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Patient added successfully"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<JsonNode> addPatient(@RequestBody JsonNode patient) {
        JsonNode response = patientServiceDelegate.addPatient(patient);
        return ResponseEntity.status(201).body(response);
    }

    @ApiOperation(value = "Update an existing patient's details")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patient updated successfully"),
            @ApiResponse(code = 404, message = "Patient not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/{patientId}")
    public ResponseEntity<JsonNode> updatePatient(@PathVariable int patientId, @RequestBody JsonNode updatedPatient) {
        JsonNode response = patientServiceDelegate.updatePatient(patientId, updatedPatient);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete a patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Patient deleted successfully"),
            @ApiResponse(code = 404, message = "Patient not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable int patientId) {
        patientServiceDelegate.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Get the list of all patients")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of patients retrieved successfully"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<JsonNode> getAllPatients() {
        JsonNode response = patientServiceDelegate.getAllPatients();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get the medical record of a patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Medical record retrieved successfully"),
            @ApiResponse(code = 404, message = "Medical record not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{patientId}/dossier-medical")
    public ResponseEntity<JsonNode> getDossierMedical(@PathVariable int patientId) {
        JsonNode response = patientServiceDelegate.getDossierMedicalByPatientId(patientId);
        return ResponseEntity.ok(response);
    }
}
