package com.howtodoinjava.example.apigateway.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.howtodoinjava.example.apigateway.delegate.DossierMedicalServiceDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("API Gateway pour la gestion des dossiers médicaux")
@RestController
@RequestMapping("/api/dossierMedical")
public class DossierMedicalController {

    @Autowired
    private DossierMedicalServiceDelegate dossierMedicalServiceDelegate;

    @ApiOperation(value = "Récupère un dossier médical par l'ID du patient")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dossier médical récupéré avec succès"),
            @ApiResponse(code = 404, message = "Dossier médical introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @GetMapping("/{idPatient}")
    public ResponseEntity<JsonNode> getDossierMedical(@PathVariable int idPatient) {
        JsonNode dossierMedical = dossierMedicalServiceDelegate.getDossierMedical(idPatient);
        return ResponseEntity.ok(dossierMedical);
    }

    @ApiOperation(value = "Ajoute un ou plusieurs identifiants de rendez-vous à un dossier médical")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rendez-vous ajoutés avec succès"),
            @ApiResponse(code = 404, message = "Dossier médical introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @PutMapping("/{idPatient}/rendezVous")
    public ResponseEntity<JsonNode> addRendezVousToDossier(
            @PathVariable int idPatient,
            @RequestBody List<Integer> rendezVousIds) {
        JsonNode response = dossierMedicalServiceDelegate.addRendezVousToDossier(idPatient, rendezVousIds);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Met à jour un dossier médical")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dossier médical mis à jour avec succès"),
            @ApiResponse(code = 404, message = "Dossier médical introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @PutMapping("/{idPatient}")
    public ResponseEntity<JsonNode> updateDossierMedical(
            @PathVariable int idPatient,
            @RequestBody JsonNode updatedDossierMedical) {
        JsonNode response = dossierMedicalServiceDelegate.updateDossierMedical(idPatient, updatedDossierMedical);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Récupère tous les dossiers médicaux")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste des dossiers médicaux récupérée avec succès"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @GetMapping
    public ResponseEntity<JsonNode> getAllDossiersMedicaux() {
        JsonNode dossiersMedicaux = dossierMedicalServiceDelegate.getAllDossiersMedicaux();
        return ResponseEntity.ok(dossiersMedicaux);
    }

    @ApiOperation(value = "Supprime un dossier médical par l'ID du patient")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dossier médical supprimé avec succès"),
            @ApiResponse(code = 404, message = "Dossier médical introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @DeleteMapping("/{idPatient}")
    public ResponseEntity<JsonNode> deleteDossierMedical(@PathVariable int idPatient) {
        JsonNode response = dossierMedicalServiceDelegate.deleteDossierMedical(idPatient);
        return ResponseEntity.ok(response);
    }
}
