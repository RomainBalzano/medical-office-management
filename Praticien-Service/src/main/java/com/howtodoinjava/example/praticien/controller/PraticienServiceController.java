package com.howtodoinjava.example.praticien.controller;

import com.howtodoinjava.example.praticien.beans.Praticien;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api("API de gestion des praticien")
@RestController
@RequestMapping("/api/praticien")
public class PraticienServiceController {

    private final List<Praticien> praticien = new ArrayList<>();

    public PraticienServiceController() {
        praticien.add(new Praticien(1, "Praticien1"));
        praticien.add(new Praticien(2, "Praticien2"));
        praticien.add(new Praticien(3, "Praticien3"));
    }

    @ApiOperation(value = "Récupère les détails d'un patient par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Praticien récupéré avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à accéder à la ressource"),
            @ApiResponse(code = 403, message = "Accès interdit à la ressource que vous essayez d'atteindre"),
            @ApiResponse(code = 404, message = "La ressource que vous essayez d'atteindre est introuvable")
    })
    @GetMapping("/{patientId}")
    public ResponseEntity<Praticien> getPraticienDetails(@PathVariable int patientId) {
        return praticien.stream()
                .filter(patient -> patient.getPraticienId() == patientId)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ApiOperation(value = "Ajoute un nouveau patient")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Praticien ajouté avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à accéder à la ressource"),
            @ApiResponse(code = 403, message = "Accès interdit à la ressource que vous essayez d'atteindre")
    })
    @PostMapping
    public ResponseEntity<Praticien> addPraticien(@RequestBody Praticien patient) {
        praticien.add(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @ApiOperation(value = "Modifie un patient existant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Praticien mis à jour avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à accéder à la ressource"),
            @ApiResponse(code = 403, message = "Accès interdit à la ressource que vous essayez d'atteindre"),
            @ApiResponse(code = 404, message = "La ressource que vous essayez d'atteindre est introuvable")
    })
    @PutMapping("/{patientId}")
    public ResponseEntity<Praticien> updatePraticien(@PathVariable int patientId, @RequestBody Praticien updatedPraticien) {
        for (int i = 0; i < praticien.size(); i++) {
            if (praticien.get(i).getPraticienId() == patientId) {
                praticien.set(i, updatedPraticien);
                return ResponseEntity.ok(updatedPraticien);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiOperation(value = "Supprime un patient par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Praticien supprimé avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à accéder à la ressource"),
            @ApiResponse(code = 403, message = "Accès interdit à la ressource que vous essayez d'atteindre"),
            @ApiResponse(code = 404, message = "La ressource que vous essayez d'atteindre est introuvable")
    })
    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePraticien(@PathVariable int patientId) {
        boolean removed = praticien.removeIf(patient -> patient.getPraticienId() == patientId);
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiOperation(value = "Récupère la liste de tous les praticien")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste des praticien récupérée avec succès"),
            @ApiResponse(code = 401, message = "Vous n'êtes pas autorisé à accéder à la ressource"),
            @ApiResponse(code = 403, message = "Accès interdit à la ressource que vous essayez d'atteindre"),
            @ApiResponse(code = 404, message = "La ressource que vous essayez d'atteindre est introuvable")
    })
    @GetMapping
    public ResponseEntity<List<Praticien>> getAllPraticiens() {
        return ResponseEntity.ok(praticien);
    }
}
