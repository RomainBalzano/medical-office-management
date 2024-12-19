CREATE DATABASE MedicalDB;

USE MedicalDB;

-- Table des patients
CREATE TABLE Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Table des praticiens
CREATE TABLE Praticien (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Table des dossiers médicaux
CREATE TABLE DossierMedical (
    idDossier INT AUTO_INCREMENT PRIMARY KEY,
    idPatient INT NOT NULL,
    FOREIGN KEY (idPatient) REFERENCES Patient(id) ON DELETE CASCADE
);

-- Table des rendez-vous
CREATE TABLE RendezVous (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    heureDebut TINYINT NOT NULL,
    minDebut TINYINT NOT NULL,
    heureFin TINYINT NOT NULL,
    minFin TINYINT NOT NULL,
    type VARCHAR(255),
    idPatient INT NOT NULL,
    idPraticien INT NOT NULL,
    status VARCHAR(50),
    FOREIGN KEY (idPatient) REFERENCES Patient(id) ON DELETE CASCADE,
    FOREIGN KEY (idPraticien) REFERENCES Praticien(id) ON DELETE CASCADE
);

-- Table pour la relation entre dossiers médicaux et rendez-vous
CREATE TABLE DossierMedical_RendezVous (
    idDossier INT NOT NULL,
    idRendezVous INT NOT NULL,
    PRIMARY KEY (idDossier, idRendezVous),
    FOREIGN KEY (idDossier) REFERENCES DossierMedical(idDossier) ON DELETE CASCADE,
    FOREIGN KEY (idRendezVous) REFERENCES RendezVous(id) ON DELETE CASCADE
);