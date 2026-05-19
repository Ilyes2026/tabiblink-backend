package com.tabiblink.dto;

public class CreateDocumentMedicalRequest {

    private String patientEmail;
    private String titre;
    private String typeDocument;
    private String cheminFichier;

    public String getPatientEmail() {
        return patientEmail;
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

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
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
}