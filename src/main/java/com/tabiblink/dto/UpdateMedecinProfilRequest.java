package com.tabiblink.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMedecinProfilRequest {
    private String nom;
    private String prenom;
    private String titre;
    private String telephoneFixe;
    private String telephoneMobile;
    private String adresseCabinet;
    private String bio;
    private Boolean conventionneCnam;
    private Integer dureeConsultation;
    private String diplomesFormations;
    private Long villeId;
    private Long delegationId;
    private Double latitude;
    private Double longitude;
}