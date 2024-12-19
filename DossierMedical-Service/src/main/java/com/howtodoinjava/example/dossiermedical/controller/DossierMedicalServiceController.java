package com.howtodoinjava.example.dossiermedical.controller;

import com.howtodoinjava.example.dossiermedical.beans.DossierMedical;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api("API de gestion des dossiers médicaux")
@RestController
@RequestMapping("/api/dossierMedical")
public class DossierMedicalServiceController {

    private final List<DossierMedical> dossiersMedicaux = new ArrayList<>();

    public DossierMedicalServiceController() {
        DossierMedical dossier1 = new DossierMedical(1, 101);
        dossier1.ajouterRendezVousId(1);
        dossier1.ajouterRendezVousId(2);

        DossierMedical dossier2 = new DossierMedical(2, 102);
        dossier2.ajouterRendezVousId(3);

        dossiersMedicaux.add(dossier1);
        dossiersMedicaux.add(dossier2);
    }

    @ApiOperation(value = "Récupère un dossier médical par l'ID du patient")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dossier médical récupéré avec succès"),
            @ApiResponse(code = 404, message = "Dossier médical introuvable")
    })
    @GetMapping("/{idPatient}")
    public ResponseEntity<DossierMedical> getDossierMedical(@PathVariable int idPatient) {
        return dossiersMedicaux.stream()
                .filter(dossier -> dossier.getIdPatient() == idPatient)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ApiOperation(value = "Ajoute un identifiant de rendez-vous à un dossier médical")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rendez-vous ajouté avec succès"),
            @ApiResponse(code = 404, message = "Dossier médical introuvable")
    })
    @PostMapping("/{idPatient}/rendezVous")
    public ResponseEntity<Void> addRendezVousToDossier(@PathVariable int idPatient, @RequestBody int rendezVousId) {
        for (DossierMedical dossier : dossiersMedicaux) {
            if (dossier.getIdPatient() == idPatient) {
                dossier.ajouterRendezVousId(rendezVousId);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiOperation(value = "Récupère tous les dossiers médicaux")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste des dossiers médicaux récupérée avec succès")
    })
    @GetMapping
    public ResponseEntity<List<DossierMedical>> getAllDossiersMedicaux() {
        return ResponseEntity.ok(dossiersMedicaux);
    }
}
