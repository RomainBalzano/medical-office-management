package com.howtodoinjava.example.rendezvous.controller;

import com.howtodoinjava.example.rendezvous.beans.RendezVous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api("API de gestion des rendezVous")
@RestController
@RequestMapping("/api/rendezVouss")
public class RendezVousServiceController {

    private final List<RendezVous> rendezVouss = new ArrayList<>();

    public RendezVousServiceController() {
        rendezVouss.add(new RendezVous(1, new Date(), 9, 30, 10, 30, "Consultation", 101, 201, "confirmé"));
        rendezVouss.add(new RendezVous(2, new Date(), 11, 0, 12, 0, "Urgence", 102, 202, "annulé"));
        rendezVouss.add(new RendezVous(3, new Date(), 14, 0, 15, 0, "Suivi", 103, 203, "en attente"));
    }

    @ApiOperation(value = "Récupère les détails d'un rendezVous par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RendezVous récupéré avec succès"),
            @ApiResponse(code = 400, message = "Requête invalide"),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Accès interdit"),
            @ApiResponse(code = 404, message = "Ressource introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @GetMapping("/{rendezVousId}")
    public ResponseEntity<RendezVous> getRendezVousDetails(@PathVariable int rendezVousId) {
        return rendezVouss.stream()
                .filter(rendezVous -> rendezVous.getRendezVousId() == rendezVousId)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ApiOperation(value = "Ajoute un nouveau rendezVous")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "RendezVous ajouté avec succès"),
            @ApiResponse(code = 400, message = "Requête invalide"),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Accès interdit"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @PostMapping
    public ResponseEntity<RendezVous> addRendezVous(@RequestBody RendezVous rendezVous) {
        rendezVouss.add(rendezVous);
        return ResponseEntity.status(HttpStatus.CREATED).body(rendezVous);
    }

    @ApiOperation(value = "Modifie un rendezVous existant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RendezVous mis à jour avec succès"),
            @ApiResponse(code = 400, message = "Requête invalide"),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Accès interdit"),
            @ApiResponse(code = 404, message = "Ressource introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @PutMapping("/{rendezVousId}")
    public ResponseEntity<RendezVous> updateRendezVous(@PathVariable int rendezVousId, @RequestBody RendezVous updatedRendezVous) {
        for (int i = 0; i < rendezVouss.size(); i++) {
            if (rendezVouss.get(i).getRendezVousId() == rendezVousId) {
                rendezVouss.set(i, updatedRendezVous);
                return ResponseEntity.ok(updatedRendezVous);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiOperation(value = "Supprime un rendezVous par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "RendezVous supprimé avec succès"),
            @ApiResponse(code = 400, message = "Requête invalide"),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Accès interdit"),
            @ApiResponse(code = 404, message = "Ressource introuvable"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @DeleteMapping("/{rendezVousId}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable int rendezVousId) {
        boolean removed = rendezVouss.removeIf(rendezVous -> rendezVous.getRendezVousId() == rendezVousId);
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiOperation(value = "Récupère la liste de tous les rendezVous")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste des rendezVous récupérée avec succès"),
            @ApiResponse(code = 400, message = "Requête invalide"),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Accès interdit"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @GetMapping
    public ResponseEntity<List<RendezVous>> getAllRendezVouss() {
        return ResponseEntity.ok(rendezVouss);
    }
}
