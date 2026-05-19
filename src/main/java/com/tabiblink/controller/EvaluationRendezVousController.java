package com.tabiblink.controller;

import com.tabiblink.dto.EvaluationRendezVousRequest;
import com.tabiblink.service.EvaluationRendezVousService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluations-rendez-vous")
@CrossOrigin("*")
public class EvaluationRendezVousController {

    private final EvaluationRendezVousService evaluationService;

    public EvaluationRendezVousController(EvaluationRendezVousService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping
    public ResponseEntity<?> creerEvaluation(@RequestBody EvaluationRendezVousRequest request) {
        try {
            return ResponseEntity.ok(evaluationService.creerEvaluation(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/rendez-vous/{rendezVousId}")
    public ResponseEntity<?> getEvaluationParRendezVous(@PathVariable Long rendezVousId) {
        try {
            return ResponseEntity.ok(evaluationService.getEvaluationParRendezVous(rendezVousId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/medecin/{medecinId}")
    public ResponseEntity<?> getEvaluationsParMedecin(@PathVariable Long medecinId) {
        return ResponseEntity.ok(evaluationService.getEvaluationsParMedecin(medecinId));
    }

    @GetMapping("/patient/{email}")
    public ResponseEntity<?> getEvaluationsParPatient(@PathVariable String email) {
        return ResponseEntity.ok(evaluationService.getEvaluationsParPatient(email));
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllEvaluations() {
        return ResponseEntity.ok(evaluationService.getAllEvaluations());
    }
}