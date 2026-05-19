package com.tabiblink.dto;

public class DisponibiliteMedecinRequest {

    private Long medecinId;
    private String jourSemaine;

    private String heureDebutMatin;
    private String heureFinMatin;

    private String heureDebutApresMidi;
    private String heureFinApresMidi;

    private Boolean actif;

    public Long getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }

    public String getJourSemaine() {
        return jourSemaine;
    }

    public void setJourSemaine(String jourSemaine) {
        this.jourSemaine = jourSemaine;
    }

    public String getHeureDebutMatin() {
        return heureDebutMatin;
    }

    public void setHeureDebutMatin(String heureDebutMatin) {
        this.heureDebutMatin = heureDebutMatin;
    }

    public String getHeureFinMatin() {
        return heureFinMatin;
    }

    public void setHeureFinMatin(String heureFinMatin) {
        this.heureFinMatin = heureFinMatin;
    }

    public String getHeureDebutApresMidi() {
        return heureDebutApresMidi;
    }

    public void setHeureDebutApresMidi(String heureDebutApresMidi) {
        this.heureDebutApresMidi = heureDebutApresMidi;
    }

    public String getHeureFinApresMidi() {
        return heureFinApresMidi;
    }

    public void setHeureFinApresMidi(String heureFinApresMidi) {
        this.heureFinApresMidi = heureFinApresMidi;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
}