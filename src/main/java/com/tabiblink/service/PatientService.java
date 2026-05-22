package com.tabiblink.service;

import com.tabiblink.dto.ChangePasswordRequest;
import com.tabiblink.dto.PatientProfileResponse;
import com.tabiblink.dto.UpdatePatientProfileRequest;
import com.tabiblink.entity.Patient;
import com.tabiblink.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import com.tabiblink.dto.PatientInfoResponse;


@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public PatientProfileResponse getPatientProfile(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));

        return new PatientProfileResponse(
                patient.getId(),
                patient.getNom(),
                patient.getPrenom(),
                patient.getEmail(),
                patient.getTelephone(),
                patient.getDateNaissance(),
                patient.getAdresse(),
                patient.getSexe(),
                patient.getDateCreation()
        );
    }

    public PatientProfileResponse updatePatientProfile(String email, UpdatePatientProfileRequest request) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));

        patient.setNom(request.getNom());
        patient.setPrenom(request.getPrenom());
        patient.setTelephone(request.getTelephone());
        patient.setDateNaissance(request.getDateNaissance());
        patient.setAdresse(request.getAdresse());
        patient.setSexe(request.getSexe());

        patientRepository.save(patient);

        return new PatientProfileResponse(
                patient.getId(),
                patient.getNom(),
                patient.getPrenom(),
                patient.getEmail(),
                patient.getTelephone(),
                patient.getDateNaissance(),
                patient.getAdresse(),
                patient.getSexe(),
                patient.getDateCreation()
        );
    }

    public void changePassword(String email, ChangePasswordRequest request) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));

        if (!passwordEncoder.matches(request.getAncienMotDePasse(), patient.getMotDePasse())) {
            throw new RuntimeException("Ancien mot de passe incorrect");
        }

        patient.setMotDePasse(passwordEncoder.encode(request.getNouveauMotDePasse()));
        patientRepository.save(patient);
    }

    public String verifierEmail(String email, String code) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));

        if (patient.getCodeVerificationEmail() == null || patient.getCodeVerificationExpireAt() == null) {
            throw new RuntimeException("Aucun code de vérification n'est disponible.");
        }

        if (patient.getCodeVerificationExpireAt().isBefore(java.time.LocalDateTime.now())) {
            throw new RuntimeException("Le code de vérification a expiré.");
        }

        if (!patient.getCodeVerificationEmail().equals(code)) {
            throw new RuntimeException("Code de vérification incorrect.");
        }

        patient.setEmailVerifie(true);
        patient.setCodeVerificationEmail(null);
        patient.setCodeVerificationExpireAt(null);

        patientRepository.save(patient);

        return "Email vérifié avec succès.";
    }

    public String envoyerCodeVerification(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient introuvable."));

        String code = String.format("%06d", new Random().nextInt(1000000));

        patient.setCodeVerificationEmail(code);
        patient.setCodeVerificationExpireAt(LocalDateTime.now().plusMinutes(10));
        patient.setEmailVerifie(false);

        patientRepository.save(patient);

        new Thread(() -> {
            try {
                emailService.envoyerCodeVerification(email, code);
                System.out.println("EMAIL ENVOYÉ AVEC SUCCÈS");
            } catch (Exception e) {
                System.out.println("ERREUR ENVOI EMAIL : " + e.getMessage());
            }
        }).start();

        return "Code envoyé";
    }

    @Autowired
    private EmailService emailService;

    public PatientInfoResponse getPatientInfo(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient introuvable."));

        return new PatientInfoResponse(
                patient.getId(),
                patient.getNom(),
                patient.getPrenom(),
                patient.getEmail(),
                patient.getTelephone(),
                patient.getSexe()
        );
    }
}