package com.tabiblink.dto;

public class DocumentMedicalResponse {

    private Long id;
    private String titre;
    private String typeDocument;
    private String cheminFichier;
    private String dateAjout;
    private boolean autorise;
    private String patientNomComplet;
    private String medecinAutoriseNomComplet;

    public DocumentMedicalResponse() {
    }

    public DocumentMedicalResponse(Long id,
                                   String titre,
                                   String typeDocument,
                                   String cheminFichier,
                                   String dateAjout,
                                   boolean autorise,
                                   String patientNomComplet,
                                   String medecinAutoriseNomComplet) {
        this.id = id;
        this.titre = titre;
        this.typeDocument = typeDocument;
        this.cheminFichier = cheminFichier;
        this.dateAjout = dateAjout;
        this.autorise = autorise;
        this.patientNomComplet = patientNomComplet;
        this.medecinAutoriseNomComplet = medecinAutoriseNomComplet;
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

    public String getDateAjout() {
        return dateAjout;
    }

    public boolean isAutorise() {
        return autorise;
    }

    public String getPatientNomComplet() {
        return patientNomComplet;
    }

    public String getMedecinAutoriseNomComplet() {
        return medecinAutoriseNomComplet;
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

    public void setDateAjout(String dateAjout) {
        this.dateAjout = dateAjout;
    }

    public void setAutorise(boolean autorise) {
        this.autorise = autorise;
    }

    public void setPatientNomComplet(String patientNomComplet) {
        this.patientNomComplet = patientNomComplet;
    }

    public void setMedecinAutoriseNomComplet(String medecinAutoriseNomComplet) {
        this.medecinAutoriseNomComplet = medecinAutoriseNomComplet;
    }
}