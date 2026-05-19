package com.tabiblink.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DocumentMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String typeDocument;

    private String cheminFichier;

    private LocalDateTime dateAjout;

    private boolean autorise;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_autorise_id")
    private Medecin medecinAutorise;

    public DocumentMedical() {
    }

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public LocalDateTime getDateAjout() {
        return dateAjout;
    }

    public boolean isAutorise() {
        return autorise;
    }

    public Patient getPatient() {
        return patient;
    }

    public Medecin getMedecinAutorise() {
        return medecinAutorise;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public void setDateAjout(LocalDateTime dateAjout) {
        this.dateAjout = dateAjout;
    }

    public void setAutorise(boolean autorise) {
        this.autorise = autorise;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setMedecinAutorise(Medecin medecinAutorise) {
        this.medecinAutorise = medecinAutorise;
    }
}