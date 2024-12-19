package com.howtodoinjava.example.apigateway.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.howtodoinjava.example.apigateway.delegate.RendezVousServiceDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("API Gateway pour la gestion des rendez-vous")
@RestController
@RequestMapping("/api/gateway/rendezVouss")
public class RendezVousController {

    @Autowired
    private RendezVousServiceDelegate rendezVousServiceDelegate;

    @ApiOperation(value = "Récupère la liste de tous les rendez-vous")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste des rendez-vous récupérée avec succès"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @GetMapping
    public ResponseEntity<JsonNode> getAllRendezVouss() {
        JsonNode response = rendezVousServiceDelegate.getAllRendezVouss();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Récupère les détails d'un rendez-vous par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rendez-vous récupéré avec succès"),
            @ApiResponse(code = 404, message = "Rendez-vous introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @GetMapping("/{rendezVousId}")
    public ResponseEntity<JsonNode> getRendezVousDetails(@PathVariable int rendezVousId) {
        JsonNode response = rendezVousServiceDelegate.getRendezVousDetails(rendezVousId);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Ajoute un nouveau rendez-vous")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Rendez-vous ajouté avec succès"),
            @ApiResponse(code = 400, message = "Requête invalide"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @PostMapping
    public ResponseEntity<JsonNode> addRendezVous(@RequestBody JsonNode rendezVous) {
        JsonNode response = rendezVousServiceDelegate.addRendezVous(rendezVous);
        return ResponseEntity.status(201).body(response);
    }

    @ApiOperation(value = "Modifie un rendez-vous existant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rendez-vous mis à jour avec succès"),
            @ApiResponse(code = 404, message = "Rendez-vous introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @PutMapping("/{rendezVousId}")
    public ResponseEntity<JsonNode> updateRendezVous(@PathVariable int rendezVousId, @RequestBody JsonNode updatedRendezVous) {
        JsonNode response = rendezVousServiceDelegate.updateRendezVous(rendezVousId, updatedRendezVous);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Supprime un rendez-vous par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Rendez-vous supprimé avec succès"),
            @ApiResponse(code = 404, message = "Rendez-vous introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @DeleteMapping("/{rendezVousId}")
    public ResponseEntity<JsonNode> deleteRendezVous(@PathVariable int rendezVousId) {
        JsonNode response = rendezVousServiceDelegate.deleteRendezVous(rendezVousId);
        return ResponseEntity.status(204).body(response);
    }
}