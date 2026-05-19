package com.tabiblink.dto;

public class RendezVousResponse {

    private Long id;
    private String patientNomComplet;
    private String medecinNomComplet;
    private String specialite;
    private String lieu;
    private String dateRendezVous;
    private String heureRendezVous;
    private String motif;
    private String statut;
    private Long medecinId;
    private String patientEmail;

    public RendezVousResponse() {
    }

    public RendezVousResponse(Long id,
                              String patientNomComplet,
                              String patientEmail,
                              Long medecinId,
                              String medecinNomComplet,
                              String specialite,
                              String lieu,
                              String dateRendezVous,
                              String heureRendezVous,
                              String motif,
                              String statut) {
        this.id = id;
        this.patientNomComplet = patientNomComplet;
        this.patientEmail = patientEmail;
        this.medecinId = medecinId;
        this.medecinNomComplet = medecinNomComplet;
        this.specialite = specialite;
        this.lieu = lieu;
        this.dateRendezVous = dateRendezVous;
        this.heureRendezVous = heureRendezVous;
        this.motif = motif;
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public String getPatientNomComplet() {
        return patientNomComplet;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    public String getMedecinNomComplet() {
        return medecinNomComplet;
    }

    public String getSpecialite() {
        return specialite;
    }

    public String getLieu() {
        return lieu;
    }

    public String getDateRendezVous() {
        return dateRendezVous;
    }

    public String getHeureRendezVous() {
        return heureRendezVous;
    }

    public String getMotif() {
        return motif;
    }

    public String getStatut() {
        return statut;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatientNomComplet(String patientNomComplet) {
        this.patientNomComplet = patientNomComplet;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }

    public void setMedecinNomComplet(String medecinNomComplet) {
        this.medecinNomComplet = medecinNomComplet;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setDateRendezVous(String dateRendezVous) {
        this.dateRendezVous = dateRendezVous;
    }

    public void setHeureRendezVous(String heureRendezVous) {
        this.heureRendezVous = heureRendezVous;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}