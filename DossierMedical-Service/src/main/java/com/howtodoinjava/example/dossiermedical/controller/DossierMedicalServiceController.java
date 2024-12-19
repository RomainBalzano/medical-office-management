package com.howtodoinjava.example.dossiermedical.controller;

import com.howtodoinjava.example.dossiermedical.beans.DossierMedical;
import com.howtodoinjava.example.dossiermedical.beans.RendezVous;
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

@Api("API de gestion des dossiers médicaux")
@RestController
@RequestMapping("/api/dossierMedical")
public class DossierMedicalServiceController {

    private final List<DossierMedical> dossiersMedicaux = new ArrayList<>();

    public DossierMedicalServiceController() {
        // Initialisation des données fictives
        DossierMedical dossier1 = new DossierMedical(1, 101);
        dossier1.ajouterRendezVous(new RendezVous(1, new Date(), 9, 30, 10, 30, "Consultation", 101, 201, "confirmé"));
        dossier1.ajouterRendezVous(new RendezVous(2, new Date(), 11, 0, 12, 0, "Urgence", 101, 202, "annulé"));

        DossierMedical dossier2 = new DossierMedical(2, 102);
        dossier2.ajouterRendezVous(new RendezVous(3, new Date(), 14, 0, 15, 0, "Suivi", 102, 203, "en attente"));

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

    @ApiOperation(value = "Ajoute un rendez-vous à un dossier médical")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rendez-vous ajouté avec succès"),
            @ApiResponse(code = 404, message = "Dossier médical introuvable")
    })
    @PostMapping("/{idPatient}/rendezVous")
    public ResponseEntity<Void> addRendezVousToDossier(@PathVariable int idPatient, @RequestBody RendezVous rendezVous) {
        for (DossierMedical dossier : dossiersMedicaux) {
            if (dossier.getIdPatient() == idPatient) {
                dossier.ajouterRendezVous(rendezVous);
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
