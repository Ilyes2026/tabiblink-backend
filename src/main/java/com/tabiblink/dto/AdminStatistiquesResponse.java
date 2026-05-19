package com.tabiblink.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminStatistiquesResponse {

    private Long totalPatients;

    private Long totalMedecins;

    private Long medecinsEnAttente;

    private Long medecinsValides;

    private Long medecinsRefuses;

    private Long totalRendezVous;

    private Long rendezVousEnAttente;

    private Long rendezVousConfirmes;

    private Long rendezVousAnnules;

    private Long rendezVousTermines;

    private Long totalSpecialites;

    private Long totalVilles;

    private Long totalDelegations;
    private Long totalEvaluations;
    private Double moyenneNoteGlobale;
    private Double moyennePonctualite;
    private Double moyenneOrganisation;
    private Double moyenneClarteInformations;
}