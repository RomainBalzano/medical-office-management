package com.howtodoinjava.example.patient.controller;

import com.howtodoinjava.example.patient.beans.Patient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api("API de gestion des patients")
@RestController
@RequestMapping("/api/patients")
public class PatientServiceController {

    private final List<Patient> patients = Arrays.asList(
            new Patient(1, "Patient1"),
            new Patient(2, "Patient2"),
            new Patient(3, "Patient3")
    );

    @ApiOperation(value = "Récupère les détails d'un patient par son ID")
    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> getPatientDetails(@PathVariable int patientId) {
        System.out.println("Fetching patient details for ID: " + patientId);

        // Recherche du patient par son ID
        return patients.stream()
                .filter(patient -> patient.getEmployeeId() == patientId)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
