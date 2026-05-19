package com.tabiblink.repository;

import com.tabiblink.entity.EvaluationRendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EvaluationRendezVousRepository extends JpaRepository<EvaluationRendezVous, Long> {

    boolean existsByRendezVousId(Long rendezVousId);

    Optional<EvaluationRendezVous> findByRendezVousId(Long rendezVousId);

    List<EvaluationRendezVous> findByMedecinIdOrderByDateEvaluationDesc(Long medecinId);

    List<EvaluationRendezVous> findByPatientEmailOrderByDateEvaluationDesc(String patientEmail);

    long countByMedecinId(Long medecinId);
}