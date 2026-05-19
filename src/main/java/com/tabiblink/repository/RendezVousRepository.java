package com.tabiblink.repository;

import com.tabiblink.entity.RendezVous;
import com.tabiblink.entity.StatutRendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.tabiblink.entity.Medecin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    boolean existsByMedecinIdAndDateRendezVousAndHeureRendezVous(
            Long medecinId,
            LocalDate dateRendezVous,
            LocalTime heureRendezVous
    );

    List<RendezVous> findByMedecinIdAndDateRendezVous(
            Long medecinId,
            LocalDate dateRendezVous
    );

    List<RendezVous> findByMedecinIdAndDateRendezVousAndStatutIn(
            Long medecinId,
            LocalDate dateRendezVous,
            List<StatutRendezVous> statuts
    );
    boolean existsByMedecinIdAndDateRendezVousAndHeureRendezVousAndStatutIn(
            Long medecinId,
            LocalDate dateRendezVous,
            LocalTime heureRendezVous,
            List<StatutRendezVous> statuts
    );



    List<RendezVous> findByPatientEmailOrderByDateRendezVousDescHeureRendezVousDesc(String patientEmail);

    List<RendezVous> findByDateRendezVousAndStatutIn(
            LocalDate dateRendezVous,
            List<StatutRendezVous> statuts
    );

    List<RendezVous> findByMedecinEmailOrderByDateRendezVousDescHeureRendezVousDesc(String medecinEmail);
    long countByStatut(StatutRendezVous statut);
    @Query("""
        SELECT DISTINCT r.medecin
        FROM RendezVous r
        WHERE r.patient.email = :patientEmail
        AND r.statut <> :statutAnnule
        """)
    List<Medecin> findMedecinsByPatientEmailAndStatutNotAnnule(
            @Param("patientEmail") String patientEmail,
            @Param("statutAnnule") StatutRendezVous statutAnnule
    );
    long countByMedecinIdAndStatut(Long medecinId, StatutRendezVous statut);

}
