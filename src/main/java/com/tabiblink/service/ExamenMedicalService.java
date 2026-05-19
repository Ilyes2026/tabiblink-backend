package com.tabiblink.service;

import com.tabiblink.dto.CreateExamenMedicalRequest;
import com.tabiblink.dto.ExamenMedicalResponse;
import com.tabiblink.entity.ExamenMedical;
import com.tabiblink.entity.RendezVous;
import com.tabiblink.repository.ExamenMedicalRepository;
import com.tabiblink.repository.RendezVousRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExamenMedicalService {

    private final ExamenMedicalRepository examenMedicalRepository;
    private final RendezVousRepository rendezVousRepository;

    public ExamenMedicalService(ExamenMedicalRepository examenMedicalRepository,
                                RendezVousRepository rendezVousRepository) {
        this.examenMedicalRepository = examenMedicalRepository;
        this.rendezVousRepository = rendezVousRepository;
    }

    public ExamenMedicalResponse prescrireExamen(CreateExamenMedicalRequest request) {
        RendezVous rendezVous = rendezVousRepository.findById(request.getRendezVousId())
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable."));

        ExamenMedical examen = new ExamenMedical();
        examen.setRendezVous(rendezVous);
        examen.setPatient(rendezVous.getPatient());
        examen.setMedecin(rendezVous.getMedecin());
        examen.setTypeExamen(request.getTypeExamen());
        examen.setDescription(request.getDescription());
        examen.setDatePrescription(LocalDate.now());

        ExamenMedical saved = examenMedicalRepository.save(examen);

        return toResponse(saved);
    }

    public List<ExamenMedicalResponse> getExamensParPatient(String patientEmail) {
        return examenMedicalRepository
                .findByPatientEmailOrderByDatePrescriptionDesc(patientEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ExamenMedicalResponse> getExamensParMedecin(String medecinEmail) {
        return examenMedicalRepository
                .findByMedecinEmailOrderByDatePrescriptionDesc(medecinEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ExamenMedicalResponse toResponse(ExamenMedical examen) {
        return new ExamenMedicalResponse(
                examen.getId(),
                examen.getTypeExamen(),
                examen.getDescription(),
                examen.getDatePrescription() != null
                        ? examen.getDatePrescription().toString()
                        : "",
                examen.getPatient().getNom() + " " + examen.getPatient().getPrenom(),
                examen.getMedecin().getNom() + " " + examen.getMedecin().getPrenom(),
                examen.getMedecin().getSpecialite() != null
                        ? examen.getMedecin().getSpecialite().getNomFr()
                        : "",
                examen.getRendezVous() != null ? examen.getRendezVous().getId() : null
        );
    }
}