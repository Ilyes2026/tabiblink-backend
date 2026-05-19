package com.tabiblink.controller;

import com.tabiblink.dto.CreateRendezVousRequest;
import com.tabiblink.dto.RendezVousResponse;
import com.tabiblink.service.RendezVousService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tabiblink.dto.ReprogrammerRendezVousRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.tabiblink.dto.ScoreFiabilitePatientResponse;



@RestController
@RequestMapping("/api/rendez-vous")
@CrossOrigin("*")
public class RendezVousController {

    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @PostMapping
    public ResponseEntity<?> creer(@RequestBody CreateRendezVousRequest request) {
        try {
            RendezVousResponse response = rendezVousService.creerRendezVous(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<?> annulerRendezVous(@PathVariable Long id) {
        try {
            rendezVousService.annulerRendezVous(id);
            return ResponseEntity.ok("Rendez-vous annulé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/patient/{email}")
    public ResponseEntity<?> getRendezVousParPatient(@PathVariable String email) {
        try {
            return ResponseEntity.ok(rendezVousService.getRendezVousParPatient(email));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/reprogrammer")
    public ResponseEntity<?> reprogrammerRendezVous(
            @PathVariable Long id,
            @RequestBody ReprogrammerRendezVousRequest request
    ) {
        try {
            rendezVousService.reprogrammerRendezVous(id, request);
            return ResponseEntity.ok("Rendez-vous reprogrammé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/rappels/envoyer-demain")
    public ResponseEntity<String> envoyerRappelsDemain() {
        int nombreEnvoyes = rendezVousService.envoyerRappelsRendezVousDemain();

        return ResponseEntity.ok(nombreEnvoyes + " rappel(s) envoyé(s).");
    }

    @GetMapping("/patient/{email}/historique")
    public ResponseEntity<?> getHistoriqueMedicalPatient(@PathVariable String email) {
        return ResponseEntity.ok(rendezVousService.getHistoriqueMedicalPatient(email));
    }

    @PutMapping("/{id}/terminer")
    public ResponseEntity<String> terminerRendezVous(@PathVariable Long id) {
        try {
            rendezVousService.terminerRendezVous(id);
            return ResponseEntity.ok("Rendez-vous terminé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/medecin/{email}")
    public ResponseEntity<?> getRendezVousParMedecin(@PathVariable String email) {
        try {
            return ResponseEntity.ok(rendezVousService.getRendezVousParMedecin(email));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/confirmer")
    public ResponseEntity<String> confirmerRendezVous(@PathVariable Long id) {
        try {
            rendezVousService.confirmerRendezVous(id);
            return ResponseEntity.ok("Rendez-vous confirmé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/patient/{patientEmail}/score-fiabilite")
    public ResponseEntity<ScoreFiabilitePatientResponse> getScoreFiabilitePatient(
            @PathVariable String patientEmail
    ) {
        ScoreFiabilitePatientResponse response =
                rendezVousService.calculerScoreFiabilitePatient(patientEmail);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/medecin/{id}/stats")
    public ResponseEntity<?> getStatsMedecin(@PathVariable Long id) {
        return ResponseEntity.ok(rendezVousService.getStatsMedecin(id));
    }
}