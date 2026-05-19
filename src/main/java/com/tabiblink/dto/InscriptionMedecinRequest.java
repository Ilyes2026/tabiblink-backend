package com.tabiblink.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class InscriptionMedecinRequest {

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String matricule;
    private String adresseCabinet;
    private String bio;
    private Long specialiteId;
    private Long villeId;
    private Long delegationId;

    private String titre;
    private String telephoneFixe;
    private String telephoneMobile;
    private Boolean conventionneCnam;
    private Integer dureeConsultation;
    private String diplomesFormations;

    private Double latitude;
    private Double longitude;

    private MultipartFile photoIdentite;
    private MultipartFile carteVisite;
    private MultipartFile carteProfessionnelle;


}