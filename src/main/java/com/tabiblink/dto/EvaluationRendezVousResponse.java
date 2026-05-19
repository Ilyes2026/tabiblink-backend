package com.tabiblink.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EvaluationRendezVousResponse {

    private Long id;

    private Long rendezVousId;

    private String patientNomComplet;

    private String medecinNomComplet;

    private String specialite;

    private String ville;

    private String delegation;

    private Integer noteGlobale;

    private Integer ponctualite;

    private Integer organisation;

    private Integer clarteInformations;

    private String commentaire;

    private String dateEvaluation;
}