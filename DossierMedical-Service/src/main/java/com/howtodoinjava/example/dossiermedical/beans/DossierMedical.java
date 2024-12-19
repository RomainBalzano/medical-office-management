package com.howtodoinjava.example.dossiermedical.beans;

import java.util.ArrayList;
import java.util.List;

public class DossierMedical {

    private int idDossier;
    private int idPatient;
    private List<RendezVous> listeRendezVous;

    public DossierMedical() {
        this.idDossier = 0;
        this.idPatient = 0;
        this.listeRendezVous = new ArrayList<>();
    }

    public DossierMedical(int idDossier, int idPatient) {
        this.idDossier = idDossier;
        this.idPatient = idPatient;
        this.listeRendezVous = new ArrayList<>();
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

    public List<RendezVous> getListeRendezVous() {
        return listeRendezVous;
    }

    public void setListeRendezVous(List<RendezVous> listeRendezVous) {
        this.listeRendezVous = listeRendezVous;
    }

    public void ajouterRendezVous(RendezVous rendezVous) {
        this.listeRendezVous.add(rendezVous);
    }
}
