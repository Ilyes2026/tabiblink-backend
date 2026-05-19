package com.tabiblink.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MedecinProfilResponse {

    private Long id;

    private String nom;
    private String prenom;
    private String email;

    private String matricule;
    private String titre;

    private String telephoneFixe;
    private String telephoneMobile;

    private String adresseCabinet;
    private String bio;

    private Boolean conventionneCnam;
    private Integer dureeConsultation;

    private String diplomesFormations;

    private Long specialiteId;
    private String specialiteNomFr;
    private String specialiteNomAr;

    private Long villeId;
    private String villeNom;

    private Long delegationId;
    private String delegationNom;

    private Double latitude;
    private Double longitude;

    private String photoIdentite;
    private String statutCompte;
}