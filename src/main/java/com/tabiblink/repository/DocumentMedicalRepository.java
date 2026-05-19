package com.tabiblink.repository;

import com.tabiblink.entity.DocumentMedical;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentMedicalRepository extends JpaRepository<DocumentMedical, Long> {

    List<DocumentMedical> findByPatientEmailOrderByDateAjoutDesc(String patientEmail);

    List<DocumentMedical> findByPatientEmailAndMedecinAutoriseEmailAndAutoriseTrueOrderByDateAjoutDesc(
            String patientEmail,
            String medecinEmail
    );
}