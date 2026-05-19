package com.tabiblink.service;

import com.tabiblink.dto.DisponibiliteMedecinRequest;
import com.tabiblink.dto.DisponibiliteMedecinResponse;
import com.tabiblink.entity.DisponibiliteMedecin;
import com.tabiblink.entity.JourSemaine;
import com.tabiblink.entity.Medecin;
import com.tabiblink.repository.DisponibiliteMedecinRepository;
import com.tabiblink.repository.MedecinRepository;
import com.tabiblink.repository.RendezVousRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

import com.tabiblink.entity.RendezVous;
import com.tabiblink.repository.RendezVousRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import com.tabiblink.entity.StatutRendezVous;

@Service
public class DisponibiliteMedecinService {

    private final DisponibiliteMedecinRepository disponibiliteRepository;
    private final MedecinRepository medecinRepository;
    private final RendezVousRepository rendezVousRepository;


    public DisponibiliteMedecinService(
            DisponibiliteMedecinRepository disponibiliteRepository,
            MedecinRepository medecinRepository,
            RendezVousRepository rendezVousRepository
    ) {
        this.disponibiliteRepository = disponibiliteRepository;
        this.medecinRepository = medecinRepository;
        this.rendezVousRepository = rendezVousRepository;
    }

    public DisponibiliteMedecinResponse ajouterOuMettreAJour(DisponibiliteMedecinRequest request) {
        Medecin medecin = medecinRepository.findById(request.getMedecinId())
                .orElseThrow(() -> new RuntimeException("Médecin introuvable"));

        JourSemaine jour = JourSemaine.valueOf(request.getJourSemaine().toUpperCase());

        DisponibiliteMedecin disponibilite = disponibiliteRepository
                .findByMedecinIdAndJourSemaine(medecin.getId(), jour)
                .orElse(new DisponibiliteMedecin());

        disponibilite.setMedecin(medecin);
        disponibilite.setJourSemaine(jour);
        disponibilite.setHeureDebutMatin(parseTime(request.getHeureDebutMatin()));
        disponibilite.setHeureFinMatin(parseTime(request.getHeureFinMatin()));
        disponibilite.setHeureDebutApresMidi(parseTime(request.getHeureDebutApresMidi()));
        disponibilite.setHeureFinApresMidi(parseTime(request.getHeureFinApresMidi()));
        disponibilite.setActif(request.getActif() != null ? request.getActif() : true);

        DisponibiliteMedecin saved = disponibiliteRepository.save(disponibilite);

        return mapToResponse(saved);
    }

    public List<DisponibiliteMedecinResponse> getDisponibilitesMedecin(Long medecinId) {
        return disponibiliteRepository.findByMedecinIdOrderByJourSemaineAsc(medecinId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private DisponibiliteMedecinResponse mapToResponse(DisponibiliteMedecin d) {
        return new DisponibiliteMedecinResponse(
                d.getId(),
                d.getMedecin().getId(),
                d.getJourSemaine().name(),
                formatTime(d.getHeureDebutMatin()),
                formatTime(d.getHeureFinMatin()),
                formatTime(d.getHeureDebutApresMidi()),
                formatTime(d.getHeureFinApresMidi()),
                d.getActif()
        );
    }

    private LocalTime parseTime(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return LocalTime.parse(value);
    }

    private String formatTime(LocalTime time) {
        return time != null ? time.toString() : null;
    }

    public List<String> getDatesDisponibles(Long medecinId) {
        List<DisponibiliteMedecin> disponibilites = disponibiliteRepository
                .findByMedecinIdOrderByJourSemaineAsc(medecinId)
                .stream()
                .filter(d -> d.getActif() != null && d.getActif())
                .toList();

        if (disponibilites.isEmpty()) {
            return List.of();
        }

        java.util.Set<JourSemaine> joursDisponibles = disponibilites.stream()
                .map(DisponibiliteMedecin::getJourSemaine)
                .collect(java.util.stream.Collectors.toSet());

        List<String> datesDisponibles = new java.util.ArrayList<>();
        java.time.LocalDate today = java.time.LocalDate.now();

        for (int i = 0; i < 30; i++) {
            java.time.LocalDate date = today.plusDays(i);
            JourSemaine jour = convertDayOfWeek(date.getDayOfWeek());

            if (joursDisponibles.contains(jour)) {
                datesDisponibles.add(date.toString());
            }
        }

        return datesDisponibles;
    }
    private JourSemaine convertDayOfWeek(java.time.DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> JourSemaine.LUNDI;
            case TUESDAY -> JourSemaine.MARDI;
            case WEDNESDAY -> JourSemaine.MERCREDI;
            case THURSDAY -> JourSemaine.JEUDI;
            case FRIDAY -> JourSemaine.VENDREDI;
            case SATURDAY -> JourSemaine.SAMEDI;
            case SUNDAY -> JourSemaine.DIMANCHE;
        };
    }

    public List<String> getHeuresDisponibles(Long medecinId, String dateString) {
        Medecin medecin = medecinRepository.findById(medecinId)
                .orElseThrow(() -> new RuntimeException("Médecin introuvable"));

        LocalDate date = LocalDate.parse(dateString);
        JourSemaine jour = convertDayOfWeek(date.getDayOfWeek());

        DisponibiliteMedecin disponibilite = disponibiliteRepository
                .findByMedecinIdAndJourSemaine(medecinId, jour)
                .orElseThrow(() -> new RuntimeException("Aucune disponibilité trouvée pour cette date."));

        if (disponibilite.getActif() == null || !disponibilite.getActif()) {
            return List.of();
        }

        Integer dureeConsultation = medecin.getDureeConsultation();
        if (dureeConsultation == null || dureeConsultation <= 0) {
            throw new RuntimeException("La durée de consultation du médecin est invalide.");
        }

        List<String> creneaux = new ArrayList<>();

        ajouterCreneaux(
                creneaux,
                disponibilite.getHeureDebutMatin(),
                disponibilite.getHeureFinMatin(),
                dureeConsultation
        );

        ajouterCreneaux(
                creneaux,
                disponibilite.getHeureDebutApresMidi(),
                disponibilite.getHeureFinApresMidi(),
                dureeConsultation
        );

        List<RendezVous> rendezVousPris = rendezVousRepository
                .findByMedecinIdAndDateRendezVousAndStatutIn(
                        medecinId,
                        date,
                        List.of(
                                StatutRendezVous.EN_ATTENTE,
                                StatutRendezVous.CONFIRME
                        )
                );

        Set<String> heuresPrises = new HashSet<>();
        for (RendezVous rdv : rendezVousPris) {
            if (rdv.getHeureRendezVous() != null) {
                heuresPrises.add(rdv.getHeureRendezVous().toString());
            }
        }

        return creneaux.stream()
                .filter(h -> !heuresPrises.contains(h))
                .toList();
    }

    private void ajouterCreneaux(List<String> creneaux, LocalTime debut, LocalTime fin, int dureeMinutes) {
        if (debut == null || fin == null) {
            return;
        }

        LocalTime courant = debut;

        while (!courant.plusMinutes(dureeMinutes).isAfter(fin)) {
            creneaux.add(courant.toString());
            courant = courant.plusMinutes(dureeMinutes);
        }
    }
}