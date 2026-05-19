package com.tabiblink.dto;

public class ExamenMedicalResponse {

    private Long id;
    private String typeExamen;
    private String description;
    private String datePrescription;
    private String patientNomComplet;
    private String medecinNomComplet;
    private String specialite;
    private Long rendezVousId;

    public ExamenMedicalResponse() {
    }

    public ExamenMedicalResponse(Long id,
                                 String typeExamen,
                                 String description,
                                 String datePrescription,
                                 String patientNomComplet,
                                 String medecinNomComplet,
                                 String specialite,
                                 Long rendezVousId) {
        this.id = id;
        this.typeExamen = typeExamen;
        this.description = description;
        this.datePrescription = datePrescription;
        this.patientNomComplet = patientNomComplet;
        this.medecinNomComplet = medecinNomComplet;
        this.specialite = specialite;
        this.rendezVousId = rendezVousId;
    }

    public Long getId() {
        return id;
    }

    public String getTypeExamen() {
        return typeExamen;
    }

    public String getDescription() {
        return description;
    }

    public String getDatePrescription() {
        return datePrescription;
    }

    public String getPatientNomComplet() {
        return patientNomComplet;
    }

    public String getMedecinNomComplet() {
        return medecinNomComplet;
    }

    public String getSpecialite() {
        return specialite;
    }

    public Long getRendezVousId() {
        return rendezVousId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTypeExamen(String typeExamen) {
        this.typeExamen = typeExamen;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatePrescription(String datePrescription) {
        this.datePrescription = datePrescription;
    }

    public void setPatientNomComplet(String patientNomComplet) {
        this.patientNomComplet = patientNomComplet;
    }

    public void setMedecinNomComplet(String medecinNomComplet) {
        this.medecinNomComplet = medecinNomComplet;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public void setRendezVousId(Long rendezVousId) {
        this.rendezVousId = rendezVousId;
    }
}