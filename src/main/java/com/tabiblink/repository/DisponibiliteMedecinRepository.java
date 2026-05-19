package com.tabiblink.repository;

import com.tabiblink.entity.DisponibiliteMedecin;
import com.tabiblink.entity.JourSemaine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DisponibiliteMedecinRepository extends JpaRepository<DisponibiliteMedecin, Long> {

    List<DisponibiliteMedecin> findByMedecinIdOrderByJourSemaineAsc(Long medecinId);

    Optional<DisponibiliteMedecin> findByMedecinIdAndJourSemaine(Long medecinId, JourSemaine jourSemaine);

    boolean existsByMedecinIdAndJourSemaine(Long medecinId, JourSemaine jourSemaine);
}