package com.tabiblink.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationRendezVousRequest {

    private Long rendezVousId;

    private Integer noteGlobale;

    private Integer ponctualite;

    private Integer organisation;

    private Integer clarteInformations;

    private String commentaire;
}