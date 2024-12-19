package com.howtodoinjava.example.dossiermedical.beans;

import java.util.Date;

public class RendezVous {

    private int id;
    private Date date;
    private int heureDebut;
    private int minDebut;
    private int heureFin;
    private int minFin;
    private String type;
    private int idPatient;
    private int idPraticien;
    private String status; // Nouveau champ pour le statut

    public RendezVous() {
        this.id = 0;
        this.date = new Date();
        this.heureDebut = 0;
        this.minDebut = 0;
        this.heureFin = 0;
        this.minFin = 0;
        this.type = "";
        this.idPatient = 0;
        this.idPraticien = 0;
        this.status = "en attente"; // Statut par défaut
    }

    public RendezVous(int id, Date date, int heureDebut, int minDebut, int heureFin, int minFin, String type, int idPatient, int idPraticien, String status) {
        this.id = id;
        this.date = date;
        this.heureDebut = heureDebut;
        this.minDebut = minDebut;
        this.heureFin = heureFin;
        this.minFin = minFin;
        this.type = type;
        this.idPatient = idPatient;
        this.idPraticien = idPraticien;
        this.status = status;
    }

    public void setRendezVousId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setHeureDebut(int heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setMinDebut(int minDebut) {
        this.minDebut = minDebut;
    }

    public void setHeureFin(int heureFin) {
        this.heureFin = heureFin;
    }

    public void setMinFin(int minFin) {
        this.minFin = minFin;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public void setIdPraticien(int idPraticien) {
        this.idPraticien = idPraticien;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRendezVousId() {
        return this.id;
    }

    public Date getDate() {
        return this.date;
    }

    public int getHeureDebut() {
        return this.heureDebut;
    }

    public int getMinDebut() {
        return this.minDebut;
    }

    public int getHeureFin() {
        return this.heureFin;
    }

    public int getMinFin() {
        return this.minFin;
    }

    public String getType() {
        return this.type;
    }

    public int getIdPatient() {
        return this.idPatient;
    }

    public int getIdPraticien() {
        return this.idPraticien;
    }

    public String getStatus() {
        return this.status;
    }
}
