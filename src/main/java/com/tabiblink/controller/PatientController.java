package com.tabiblink.controller;

import com.tabiblink.dto.ChangePasswordRequest;
import com.tabiblink.dto.PatientProfileResponse;
import com.tabiblink.dto.UpdatePatientProfileRequest;
import com.tabiblink.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.tabiblink.dto.VerifyEmailCodeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.tabiblink.dto.EnvoyerCodeVerificationRequest;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/profile")
    public PatientProfileResponse getProfile(@RequestParam String email) {
        return patientService.getPatientProfile(email);
    }

    @PutMapping("/profile")
    public PatientProfileResponse updateProfile(
            @RequestParam String email,
            @RequestBody UpdatePatientProfileRequest request
    ) {
        return patientService.updatePatientProfile(email, request);
    }

    @PutMapping("/change-password")
    public String changePassword(
            @RequestParam String email,
            @RequestBody ChangePasswordRequest request
    ) {
        patientService.changePassword(email, request);
        return "Mot de passe modifié avec succès";
    }

    @PostMapping("/verifier-email")
    public ResponseEntity<?> verifierEmail(@RequestBody VerifyEmailCodeRequest request) {
        try {
            String message = patientService.verifierEmail(request.getEmail(), request.getCode());
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/envoyer-code-verification")
    public ResponseEntity<?> envoyerCodeVerification(@RequestBody EnvoyerCodeVerificationRequest request) {
        try {
            patientService.envoyerCodeVerification(request.getEmail());
            return ResponseEntity.ok("Code envoyé avec succès à votre adresse email.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/info/{email:.+}")
    public ResponseEntity<?> getPatientInfo(@PathVariable String email) {
        try {
            return ResponseEntity.ok(patientService.getPatientInfo(email));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}