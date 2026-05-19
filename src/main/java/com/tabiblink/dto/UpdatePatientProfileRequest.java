package com.tabiblink.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdatePatientProfileRequest {

    private String nom;
    private String prenom;
    private String telephone;
    private LocalDate dateNaissance;
    private String adresse;
    private String sexe;
}