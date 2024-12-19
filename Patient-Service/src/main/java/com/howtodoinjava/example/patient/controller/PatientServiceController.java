package com.howtodoinjava.example.patient.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.howtodoinjava.example.patient.beans.Patient;
import com.howtodoinjava.example.patient.delegate.PatientServiceDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api("API de gestion des patients")
@RestController
@RequestMapping("/api/patients")
public class PatientServiceController {

    private final List<Patient> patients = new ArrayList<>();

    @Autowired
    private PatientServiceDelegate patientServiceDelegate;

    public PatientServiceController() {
        patients.add(new Patient(1, "Patient1"));
        patients.add(new Patient(2, "Patient2"));
        patients.add(new Patient(3, "Patient3"));
    }

    @ApiOperation(value = "Récupère les détails d'un patient par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patient récupéré avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à accéder à la ressource"),
            @ApiResponse(code = 403, message = "Accès interdit à la ressource que vous essayez d'atteindre"),
            @ApiResponse(code = 404, message = "La ressource que vous essayez d'atteindre est introuvable")
    })
    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> getPatientDetails(@PathVariable int patientId) {
        return patients.stream()
                .filter(patient -> patient.getPatientId() == patientId)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ApiOperation(value = "Ajoute un nouveau patient")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Patient ajouté avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à accéder à la ressource"),
            @ApiResponse(code = 403, message = "Accès interdit à la ressource que vous essayez d'atteindre")
    })
    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        patients.add(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @ApiOperation(value = "Modifie un patient existant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patient mis à jour avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à accéder à la ressource"),
            @ApiResponse(code = 403, message = "Accès interdit à la ressource que vous essayez d'atteindre"),
            @ApiResponse(code = 404, message = "La ressource que vous essayez d'atteindre est introuvable")
    })
    @PutMapping("/{patientId}")
    public ResponseEntity<Patient> updatePatient(@PathVariable int patientId, @RequestBody Patient updatedPatient) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientId() == patientId) {
                patients.set(i, updatedPatient);
                return ResponseEntity.ok(updatedPatient);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiOperation(value = "Supprime un patient par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Patient supprimé avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à accéder à la ressource"),
            @ApiResponse(code = 403, message = "Accès interdit à la ressource que vous essayez d'atteindre"),
            @ApiResponse(code = 404, message = "La ressource que vous essayez d'atteindre est introuvable")
    })
    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable int patientId) {
        boolean removed = patients.removeIf(patient -> patient.getPatientId() == patientId);
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiOperation(value = "Récupère la liste de tous les patients")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste des patients récupérée avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à accéder à la ressource"),
            @ApiResponse(code = 403, message = "Accès interdit à la ressource que vous essayez d'atteindre"),
            @ApiResponse(code = 404, message = "La ressource que vous essayez d'atteindre est introuvable")
    })
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patients);
    }

    @ApiOperation(value = "Récupère le dossier médical d'un patient par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dossier médical récupéré avec succès"),
            @ApiResponse(code = 404, message = "Le dossier médical est introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @GetMapping("/{patientId}/dossier-medical")
    public ResponseEntity<JsonNode> getDossierMedical(@PathVariable int patientId) {
            JsonNode dossierMedical = patientServiceDelegate.getDossierMedicalByPatientId(patientId);
            return ResponseEntity.ok(dossierMedical);

    }
}
