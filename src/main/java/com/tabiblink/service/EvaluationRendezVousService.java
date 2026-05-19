package com.tabiblink.service;

import com.tabiblink.dto.EvaluationRendezVousRequest;
import com.tabiblink.dto.EvaluationRendezVousResponse;
import com.tabiblink.entity.EvaluationRendezVous;
import com.tabiblink.entity.RendezVous;
import com.tabiblink.repository.EvaluationRendezVousRepository;
import com.tabiblink.repository.RendezVousRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationRendezVousService {

    private final EvaluationRendezVousRepository evaluationRepository;
    private final RendezVousRepository rendezVousRepository;

    public EvaluationRendezVousService(EvaluationRendezVousRepository evaluationRepository,
                                       RendezVousRepository rendezVousRepository) {
        this.evaluationRepository = evaluationRepository;
        this.rendezVousRepository = rendezVousRepository;
    }

    public EvaluationRendezVousResponse creerEvaluation(EvaluationRendezVousRequest request) {
        RendezVous rendezVous = rendezVousRepository.findById(request.getRendezVousId())
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable."));

        if (evaluationRepository.existsByRendezVousId(request.getRendezVousId())) {
            throw new RuntimeException("Ce rendez-vous a déjà été évalué.");
        }

        verifierNote(request.getNoteGlobale(), "note globale");
        verifierNote(request.getPonctualite(), "ponctualité");
        verifierNote(request.getOrganisation(), "organisation");
        verifierNote(request.getClarteInformations(), "clarté des informations");

        EvaluationRendezVous evaluation = new EvaluationRendezVous();
        evaluation.setRendezVous(rendezVous);
        evaluation.setPatient(rendezVous.getPatient());
        evaluation.setMedecin(rendezVous.getMedecin());
        evaluation.setNoteGlobale(request.getNoteGlobale());
        evaluation.setPonctualite(request.getPonctualite());
        evaluation.setOrganisation(request.getOrganisation());
        evaluation.setClarteInformations(request.getClarteInformations());
        evaluation.setCommentaire(request.getCommentaire());

        EvaluationRendezVous saved = evaluationRepository.save(evaluation);

        return toResponse(saved);
    }

    public EvaluationRendezVousResponse getEvaluationParRendezVous(Long rendezVousId) {
        EvaluationRendezVous evaluation = evaluationRepository.findByRendezVousId(rendezVousId)
                .orElseThrow(() -> new RuntimeException("Aucune évaluation trouvée pour ce rendez-vous."));

        return toResponse(evaluation);
    }

    public List<EvaluationRendezVousResponse> getEvaluationsParMedecin(Long medecinId) {
        return evaluationRepository.findByMedecinIdOrderByDateEvaluationDesc(medecinId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EvaluationRendezVousResponse> getEvaluationsParPatient(String patientEmail) {
        return evaluationRepository.findByPatientEmailOrderByDateEvaluationDesc(patientEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EvaluationRendezVousResponse> getAllEvaluations() {
        return evaluationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private void verifierNote(Integer note, String champ) {
        if (note == null || note < 1 || note > 5) {
            throw new RuntimeException("La " + champ + " doit être entre 1 et 5.");
        }
    }

    private EvaluationRendezVousResponse toResponse(EvaluationRendezVous evaluation) {
        String patientNomComplet =
                evaluation.getPatient().getPrenom() + " " + evaluation.getPatient().getNom();

        String medecinNomComplet =
                evaluation.getMedecin().getPrenom() + " " + evaluation.getMedecin().getNom();

        String specialite = evaluation.getMedecin().getSpecialite() != null
                ? evaluation.getMedecin().getSpecialite().getNomFr()
                : "Spécialité non précisée";

        String ville = evaluation.getMedecin().getVille() != null
                ? evaluation.getMedecin().getVille().getNomVille()
                : "Ville non précisée";

        String delegation = evaluation.getMedecin().getDelegation() != null
                ? evaluation.getMedecin().getDelegation().getNomDelegation()
                : "Délégation non précisée";

        return new EvaluationRendezVousResponse(
                evaluation.getId(),
                evaluation.getRendezVous().getId(),
                patientNomComplet,
                medecinNomComplet,
                specialite,
                ville,
                delegation,
                evaluation.getNoteGlobale(),
                evaluation.getPonctualite(),
                evaluation.getOrganisation(),
                evaluation.getClarteInformations(),
                evaluation.getCommentaire(),
                evaluation.getDateEvaluation().toString()
        );
    }
}