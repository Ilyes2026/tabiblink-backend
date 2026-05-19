package com.tabiblink.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MedecinRechercheResponse {

    private Long id;
    private String nom;
    private String prenom;
    private String nomComplet;
    private String titre;
    private String bio;
    private String adresseCabinet;
    private String telephoneFixe;
    private String telephoneMobile;
    private Boolean conventionneCnam;
    private String photoIdentite;
    private String carteVisite;
    private String specialiteFr;
    private String specialiteAr;
    private String ville;
    private String delegation;
    private Integer dureeConsultation;
    private String diplomesFormations;
    private Double latitude;
    private Double longitude;
}