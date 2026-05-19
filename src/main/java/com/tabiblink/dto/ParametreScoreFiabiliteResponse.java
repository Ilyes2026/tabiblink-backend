package com.tabiblink.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametreScoreFiabiliteResponse {

    private Long id;

    private Integer scoreInitial;

    private Integer penaliteAnnulationTardive;

    private Integer penaliteAbsence;

    private Integer heuresAnnulationTardive;

    private Integer seuilTresFiable;

    private Integer seuilFiable;

    private Integer seuilMoyen;
}