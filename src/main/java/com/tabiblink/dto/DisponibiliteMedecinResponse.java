package com.tabiblink.dto;

public class DisponibiliteMedecinResponse {

    private Long id;
    private Long medecinId;
    private String jourSemaine;

    private String heureDebutMatin;
    private String heureFinMatin;

    private String heureDebutApresMidi;
    private String heureFinApresMidi;

    private Boolean actif;

    public DisponibiliteMedecinResponse(
            Long id,
            Long medecinId,
            String jourSemaine,
            String heureDebutMatin,
            String heureFinMatin,
            String heureDebutApresMidi,
            String heureFinApresMidi,
            Boolean actif
    ) {
        this.id = id;
        this.medecinId = medecinId;
        this.jourSemaine = jourSemaine;
        this.heureDebutMatin = heureDebutMatin;
        this.heureFinMatin = heureFinMatin;
        this.heureDebutApresMidi = heureDebutApresMidi;
        this.heureFinApresMidi = heureFinApresMidi;
        this.actif = actif;
    }

    public Long getId() {
        return id;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    public String getJourSemaine() {
        return jourSemaine;
    }

    public String getHeureDebutMatin() {
        return heureDebutMatin;
    }

    public String getHeureFinMatin() {
        return heureFinMatin;
    }

    public String getHeureDebutApresMidi() {
        return heureDebutApresMidi;
    }

    public String getHeureFinApresMidi() {
        return heureFinApresMidi;
    }

    public Boolean getActif() {
        return actif;
    }
}