package com.tabiblink.service;

import com.tabiblink.dto.AdminStatistiquesResponse;
import com.tabiblink.entity.EvaluationRendezVous;
import com.tabiblink.entity.StatutCompte;
import com.tabiblink.entity.StatutRendezVous;
import com.tabiblink.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminStatistiquesService {

    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final RendezVousRepository rendezVousRepository;
    private final SpecialiteRepository specialiteRepository;
    private final VilleRepository villeRepository;
    private final DelegationRepository delegationRepository;
    private final EvaluationRendezVousRepository evaluationRepository;

    public AdminStatistiquesService(PatientRepository patientRepository,
                                    MedecinRepository medecinRepository,
                                    RendezVousRepository rendezVousRepository,
                                    SpecialiteRepository specialiteRepository,
                                    VilleRepository villeRepository,
                                    DelegationRepository delegationRepository,
                                    EvaluationRendezVousRepository evaluationRepository) {
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.specialiteRepository = specialiteRepository;
        this.villeRepository = villeRepository;
        this.delegationRepository = delegationRepository;
        this.evaluationRepository = evaluationRepository;
    }

    public AdminStatistiquesResponse getStatistiques() {

        List<EvaluationRendezVous> evaluations = evaluationRepository.findAll();

        long totalEvaluations = evaluations.size();

        double moyenneNoteGlobale = evaluations.stream()
                .mapToInt(EvaluationRendezVous::getNoteGlobale)
                .average()
                .orElse(0);

        double moyennePonctualite = evaluations.stream()
                .mapToInt(EvaluationRendezVous::getPonctualite)
                .average()
                .orElse(0);

        double moyenneOrganisation = evaluations.stream()
                .mapToInt(EvaluationRendezVous::getOrganisation)
                .average()
                .orElse(0);

        double moyenneClarteInformations = evaluations.stream()
                .mapToInt(EvaluationRendezVous::getClarteInformations)
                .average()
                .orElse(0);

        return new AdminStatistiquesResponse(
                patientRepository.count(),
                medecinRepository.count(),
                medecinRepository.countByStatutCompte(StatutCompte.EN_ATTENTE),
                medecinRepository.countByStatutCompte(StatutCompte.VALIDE),
                medecinRepository.countByStatutCompte(StatutCompte.REFUSE),
                rendezVousRepository.count(),
                rendezVousRepository.countByStatut(StatutRendezVous.EN_ATTENTE),
                rendezVousRepository.countByStatut(StatutRendezVous.CONFIRME),
                rendezVousRepository.countByStatut(StatutRendezVous.ANNULE),
                rendezVousRepository.countByStatut(StatutRendezVous.TERMINE),
                specialiteRepository.count(),
                villeRepository.count(),
                delegationRepository.count(),
                totalEvaluations,
                moyenneNoteGlobale,
                moyennePonctualite,
                moyenneOrganisation,
                moyenneClarteInformations
        );
    }
}