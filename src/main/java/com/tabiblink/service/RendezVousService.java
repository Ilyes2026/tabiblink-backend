package com.tabiblink.service;

import com.tabiblink.dto.CreateRendezVousRequest;
import com.tabiblink.dto.RendezVousResponse;
import com.tabiblink.dto.ReprogrammerRendezVousRequest;
import com.tabiblink.dto.ScoreFiabilitePatientResponse;
import com.tabiblink.entity.Medecin;
import com.tabiblink.entity.ParametreScoreFiabilite;
import com.tabiblink.entity.Patient;
import com.tabiblink.entity.RendezVous;
import com.tabiblink.entity.StatutRendezVous;
import com.tabiblink.repository.EvaluationRendezVousRepository;
import com.tabiblink.repository.MedecinRepository;
import com.tabiblink.repository.PatientRepository;
import com.tabiblink.repository.RendezVousRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final EmailService emailService;
    private final ParametreScoreFiabiliteService parametreScoreFiabiliteService;
    private final EvaluationRendezVousRepository evaluationRendezVousRepository;
    public RendezVousService(RendezVousRepository rendezVousRepository,
                             PatientRepository patientRepository,
                             MedecinRepository medecinRepository,
                             EmailService emailService,
                             ParametreScoreFiabiliteService parametreScoreFiabiliteService,
                             EvaluationRendezVousRepository evaluationRendezVousRepository) {

        this.rendezVousRepository = rendezVousRepository;
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.emailService = emailService;
        this.parametreScoreFiabiliteService = parametreScoreFiabiliteService;
        this.evaluationRendezVousRepository = evaluationRendezVousRepository;
    }

    public RendezVousResponse creerRendezVous(CreateRendezVousRequest request) {
        Patient patient = patientRepository.findByEmail(request.getPatientEmail())
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));

        Medecin medecin = medecinRepository.findById(request.getMedecinId())
                .orElseThrow(() -> new RuntimeException("Médecin introuvable"));

        if (patient.getEmailVerifie() == null || !patient.getEmailVerifie()) {
            throw new RuntimeException("Veuillez vérifier votre adresse email avant de confirmer le rendez-vous.");
        }

        LocalDate date = LocalDate.parse(request.getDateRendezVous());
        LocalTime heure = LocalTime.parse(request.getHeureRendezVous());

        boolean existe = rendezVousRepository
                .existsByMedecinIdAndDateRendezVousAndHeureRendezVousAndStatutIn(
                        medecin.getId(),
                        date,
                        heure,
                        List.of(
                                StatutRendezVous.EN_ATTENTE,
                                StatutRendezVous.CONFIRME
                        )
                );

        if (existe) {
            throw new RuntimeException("Ce créneau est déjà réservé.");
        }

        RendezVous rdv = new RendezVous();
        rdv.setPatient(patient);
        rdv.setMedecin(medecin);
        rdv.setDateRendezVous(date);
        rdv.setHeureRendezVous(heure);
        rdv.setMotif(request.getMotif());
        rdv.setStatut(StatutRendezVous.EN_ATTENTE);

        RendezVous saved = rendezVousRepository.save(rdv);

        return toResponse(saved);
    }

    public void annulerRendezVous(Long rendezVousId) {
        RendezVous rendezVous = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable."));

        rendezVous.setStatut(StatutRendezVous.ANNULE);
        rendezVous.setDateAnnulation(LocalDateTime.now());

        rendezVousRepository.save(rendezVous);
    }

    public List<RendezVousResponse> getRendezVousParPatient(String patientEmail) {
        List<RendezVous> rendezVousList =
                rendezVousRepository.findByPatientEmailOrderByDateRendezVousDescHeureRendezVousDesc(patientEmail);

        return rendezVousList.stream()
                .map(this::toResponse)
                .toList();
    }

    public void reprogrammerRendezVous(Long rendezVousId, ReprogrammerRendezVousRequest request) {
        RendezVous rendezVous = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable."));

        if (rendezVous.getStatut() == StatutRendezVous.ANNULE) {
            throw new RuntimeException("Impossible de reprogrammer un rendez-vous annulé.");
        }

        LocalDate nouvelleDate = LocalDate.parse(request.getDateRendezVous());
        LocalTime nouvelleHeure = LocalTime.parse(request.getHeureRendezVous());

        boolean existe = rendezVousRepository
                .existsByMedecinIdAndDateRendezVousAndHeureRendezVousAndStatutIn(
                        rendezVous.getMedecin().getId(),
                        nouvelleDate,
                        nouvelleHeure,
                        List.of(
                                StatutRendezVous.EN_ATTENTE,
                                StatutRendezVous.CONFIRME
                        )
                );

        boolean memeCreneau =
                rendezVous.getDateRendezVous().equals(nouvelleDate) &&
                        rendezVous.getHeureRendezVous().equals(nouvelleHeure);

        if (existe && !memeCreneau) {
            throw new RuntimeException("Ce nouveau créneau est déjà réservé.");
        }

        rendezVous.setDateRendezVous(nouvelleDate);
        rendezVous.setHeureRendezVous(nouvelleHeure);
        rendezVous.setMotif(request.getMotif());
        rendezVous.setStatut(StatutRendezVous.EN_ATTENTE);

        rendezVousRepository.save(rendezVous);
    }

    public int envoyerRappelsRendezVousDemain() {
        LocalDate demain = LocalDate.now().plusDays(1);

        List<StatutRendezVous> statutsARappeler = List.of(
                StatutRendezVous.EN_ATTENTE,
                StatutRendezVous.CONFIRME
        );

        List<RendezVous> rendezVousDemain =
                rendezVousRepository.findByDateRendezVousAndStatutIn(
                        demain,
                        statutsARappeler
                );

        int nombreEnvoyes = 0;

        for (RendezVous rdv : rendezVousDemain) {
            String patientEmail = rdv.getPatient().getEmail();

            String patientNomComplet =
                    rdv.getPatient().getPrenom() + " " + rdv.getPatient().getNom();

            String medecinNomComplet =
                    rdv.getMedecin().getPrenom() + " " + rdv.getMedecin().getNom();

            String specialite = rdv.getMedecin().getSpecialite() != null
                    ? rdv.getMedecin().getSpecialite().getNomFr()
                    : "Spécialité non précisée";

            String lieu = rdv.getMedecin().getAdresseCabinet() != null
                    ? rdv.getMedecin().getAdresseCabinet()
                    : "Lieu non précisé";

            emailService.envoyerRappelRendezVous(
                    patientEmail,
                    patientNomComplet,
                    medecinNomComplet,
                    specialite,
                    lieu,
                    rdv.getDateRendezVous().toString(),
                    rdv.getHeureRendezVous().toString()
            );

            nombreEnvoyes++;
        }

        return nombreEnvoyes;
    }

    public List<RendezVousResponse> getHistoriqueMedicalPatient(String patientEmail) {
        List<RendezVous> rendezVousList =
                rendezVousRepository.findByPatientEmailOrderByDateRendezVousDescHeureRendezVousDesc(patientEmail);

        return rendezVousList.stream()
                .map(this::toResponse)
                .toList();
    }

    public void terminerRendezVous(Long rendezVousId) {
        RendezVous rendezVous = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable."));

        if (rendezVous.getStatut() == StatutRendezVous.ANNULE) {
            throw new RuntimeException("Impossible de terminer un rendez-vous annulé.");
        }

        rendezVous.setStatut(StatutRendezVous.TERMINE);
        rendezVousRepository.save(rendezVous);
    }

    public List<RendezVousResponse> getRendezVousParMedecin(String medecinEmail) {
        List<RendezVous> rendezVousList =
                rendezVousRepository.findByMedecinEmailOrderByDateRendezVousDescHeureRendezVousDesc(medecinEmail);

        return rendezVousList.stream()
                .map(this::toResponse)
                .toList();
    }

    public void confirmerRendezVous(Long rendezVousId) {
        RendezVous rendezVous = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable."));

        if (rendezVous.getStatut() == StatutRendezVous.ANNULE) {
            throw new RuntimeException("Impossible de confirmer un rendez-vous annulé.");
        }

        if (rendezVous.getStatut() == StatutRendezVous.TERMINE) {
            throw new RuntimeException("Impossible de confirmer un rendez-vous terminé.");
        }

        rendezVous.setStatut(StatutRendezVous.CONFIRME);
        rendezVousRepository.save(rendezVous);
    }

    private RendezVousResponse toResponse(RendezVous rdv) {
        return new RendezVousResponse(
                rdv.getId(),
                rdv.getPatient().getNom() + " " + rdv.getPatient().getPrenom(),
                rdv.getPatient().getEmail(),
                rdv.getMedecin().getId(),
                rdv.getMedecin().getNom() + " " + rdv.getMedecin().getPrenom(),
                rdv.getMedecin().getSpecialite() != null
                        ? rdv.getMedecin().getSpecialite().getNomFr()
                        : "",
                rdv.getMedecin().getAdresseCabinet() != null
                        ? rdv.getMedecin().getAdresseCabinet()
                        : "",
                rdv.getDateRendezVous().toString(),
                rdv.getHeureRendezVous().toString(),
                rdv.getMotif(),
                rdv.getStatut().name()
        );
    }

    public ScoreFiabilitePatientResponse calculerScoreFiabilitePatient(String patientEmail) {
        Patient patient = patientRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new RuntimeException("Patient introuvable."));

        ParametreScoreFiabilite parametres =
                parametreScoreFiabiliteService.getOrCreateParametres();

        List<RendezVous> rendezVousList =
                rendezVousRepository.findByPatientEmailOrderByDateRendezVousDescHeureRendezVousDesc(patientEmail);

        int totalRendezVous = rendezVousList.size();
        int rendezVousTermines = 0;
        int annulationsTardives = 0;

        for (RendezVous rdv : rendezVousList) {
            if (rdv.getStatut() == StatutRendezVous.TERMINE) {
                rendezVousTermines++;
            }

            if (rdv.getStatut() == StatutRendezVous.ANNULE
                    && rdv.getDateAnnulation() != null
                    && rdv.getDateRendezVous() != null
                    && rdv.getHeureRendezVous() != null) {

                LocalDateTime dateHeureRendezVous = LocalDateTime.of(
                        rdv.getDateRendezVous(),
                        rdv.getHeureRendezVous()
                );

                LocalDateTime limiteAnnulation =
                        dateHeureRendezVous.minusHours(parametres.getHeuresAnnulationTardive());

                if (rdv.getDateAnnulation().isAfter(limiteAnnulation)) {
                    annulationsTardives++;
                }
            }
        }

        int score = parametres.getScoreInitial()
                - (annulationsTardives * parametres.getPenaliteAnnulationTardive());

        if (score < 0) {
            score = 0;
        }

        if (score > 100) {
            score = 100;
        }

        String niveau;

        if (score >= parametres.getSeuilTresFiable()) {
            niveau = "Très fiable";
        } else if (score >= parametres.getSeuilFiable()) {
            niveau = "Fiable";
        } else if (score >= parametres.getSeuilMoyen()) {
            niveau = "Moyen";
        } else {
            niveau = "Faible";
        }

        return new ScoreFiabilitePatientResponse(
                patient.getEmail(),
                score,
                niveau,
                totalRendezVous,
                rendezVousTermines,
                annulationsTardives
        );
    }

    public Map<String, Long> getStatsMedecin(Long medecinId) {

        long demandesEnAttente = rendezVousRepository.countByMedecinIdAndStatut(
                medecinId, StatutRendezVous.EN_ATTENTE
        );

        long rendezVousConfirmes = rendezVousRepository.countByMedecinIdAndStatut(
                medecinId, StatutRendezVous.CONFIRME
        );

        long evaluationsRecues = evaluationRendezVousRepository.countByMedecinId(medecinId);

        Map<String, Long> stats = new HashMap<>();
        stats.put("demandesEnAttente", demandesEnAttente);
        stats.put("rendezVousConfirmes", rendezVousConfirmes);
        stats.put("evaluationsRecues", evaluationsRecues);

        return stats;
    }
}