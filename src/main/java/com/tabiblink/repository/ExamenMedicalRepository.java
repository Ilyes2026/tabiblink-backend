package com.tabiblink.repository;

import com.tabiblink.entity.ExamenMedical;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamenMedicalRepository extends JpaRepository<ExamenMedical, Long> {

    List<ExamenMedical> findByPatientEmailOrderByDatePrescriptionDesc(String patientEmail);

    List<ExamenMedical> findByMedecinEmailOrderByDatePrescriptionDesc(String medecinEmail);
}