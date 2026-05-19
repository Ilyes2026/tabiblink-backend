package com.tabiblink.dto;

public class MedecinSimpleResponse {

    private Long id;
    private String nomComplet;
    private String specialite;
    private String ville;

    public MedecinSimpleResponse() {
    }

    public MedecinSimpleResponse(Long id, String nomComplet, String specialite, String ville) {
        this.id = id;
        this.nomComplet = nomComplet;
        this.specialite = specialite;
        this.ville = ville;
    }

    public Long getId() {
        return id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public String getSpecialite() {
        return specialite;
    }

    public String getVille() {
        return ville;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}