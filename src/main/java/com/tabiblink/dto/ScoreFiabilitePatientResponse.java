package com.tabiblink.dto;

public class ScoreFiabilitePatientResponse {

    private String patientEmail;
    private int score;
    private String niveau;
    private int totalRendezVous;
    private int rendezVousTermines;
    private int annulationsTardives;

    public ScoreFiabilitePatientResponse() {
    }

    public ScoreFiabilitePatientResponse(String patientEmail,
                                         int score,
                                         String niveau,
                                         int totalRendezVous,
                                         int rendezVousTermines,
                                         int annulationsTardives) {
        this.patientEmail = patientEmail;
        this.score = score;
        this.niveau = niveau;
        this.totalRendezVous = totalRendezVous;
        this.rendezVousTermines = rendezVousTermines;
        this.annulationsTardives = annulationsTardives;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public int getTotalRendezVous() {
        return totalRendezVous;
    }

    public void setTotalRendezVous(int totalRendezVous) {
        this.totalRendezVous = totalRendezVous;
    }

    public int getRendezVousTermines() {
        return rendezVousTermines;
    }

    public void setRendezVousTermines(int rendezVousTermines) {
        this.rendezVousTermines = rendezVousTermines;
    }

    public int getAnnulationsTardives() {
        return annulationsTardives;
    }

    public void setAnnulationsTardives(int annulationsTardives) {
        this.annulationsTardives = annulationsTardives;
    }
}