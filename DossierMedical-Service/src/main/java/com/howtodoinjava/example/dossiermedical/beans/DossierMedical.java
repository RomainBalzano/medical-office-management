package com.howtodoinjava.example.dossiermedical.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un dossier médical qui centralise les informations liées à un patient
 * et ses rendez-vous avec les praticiens.
 */
public class DossierMedical {

    private int idDossier;
    private int idPatient;
    private List<Integer> rendezVousIds;

    public DossierMedical() {
        this.idDossier = 0;
        this.idPatient = 0;
        this.rendezVousIds = new ArrayList<>();
    }

    public DossierMedical(int idDossier, int idPatient) {
        this.idDossier = idDossier;
        this.idPatient = idPatient;
        this.rendezVousIds = new ArrayList<>();
    }

    public int getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(int idDossier) {
        this.idDossier = idDossier;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public List<Integer> getRendezVousIds() {
        return rendezVousIds;
    }

    public void setRendezVousIds(List<Integer> rendezVousIds) {
        this.rendezVousIds = rendezVousIds;
    }

    public void ajouterRendezVousId(int rendezVousId) {
        this.rendezVousIds.add(rendezVousId);
    }
}
