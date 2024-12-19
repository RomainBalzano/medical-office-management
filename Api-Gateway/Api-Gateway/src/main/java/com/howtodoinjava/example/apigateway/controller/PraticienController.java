package com.howtodoinjava.example.apigateway.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.howtodoinjava.example.apigateway.delegate.PraticienServiceDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("API Gateway Controller for Praticien Service")
@RestController
@RequestMapping("/api/praticien")
public class PraticienController {

    @Autowired
    private PraticienServiceDelegate praticienServiceDelegate;

    @ApiOperation(value = "Récupère les détails d'un praticien par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Praticien récupéré avec succès"),
            @ApiResponse(code = 404, message = "Praticien introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @GetMapping("/{praticienId}")
    public ResponseEntity<JsonNode> getPraticienDetails(@PathVariable int praticienId) {
        JsonNode response = praticienServiceDelegate.getPraticienDetails(praticienId);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Ajoute un nouveau praticien")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Praticien ajouté avec succès"),
            @ApiResponse(code = 400, message = "Requête invalide"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @PostMapping
    public ResponseEntity<JsonNode> addPraticien(@RequestBody JsonNode praticien) {
        JsonNode response = praticienServiceDelegate.addPraticien(praticien);
        return ResponseEntity.status(201).body(response);
    }

    @ApiOperation(value = "Modifie un praticien existant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Praticien mis à jour avec succès"),
            @ApiResponse(code = 404, message = "Praticien introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @PutMapping("/{praticienId}")
    public ResponseEntity<JsonNode> updatePraticien(@PathVariable int praticienId, @RequestBody JsonNode updatedPraticien) {
        JsonNode response = praticienServiceDelegate.updatePraticien(praticienId, updatedPraticien);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Supprime un praticien par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Praticien supprimé avec succès"),
            @ApiResponse(code = 404, message = "Praticien introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @DeleteMapping("/{praticienId}")
    public ResponseEntity<JsonNode> deletePraticien(@PathVariable int praticienId) {
        JsonNode response = praticienServiceDelegate.deletePraticien(praticienId);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Récupère la liste de tous les praticiens")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste des praticiens récupérée avec succès"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @GetMapping
    public ResponseEntity<JsonNode> getAllPraticiens() {
        JsonNode response = praticienServiceDelegate.getAllPraticiens();
        return ResponseEntity.ok(response);
    }
}
