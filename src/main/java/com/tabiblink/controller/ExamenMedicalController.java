package com.tabiblink.controller;

import com.tabiblink.dto.CreateExamenMedicalRequest;
import com.tabiblink.service.ExamenMedicalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/examens")
@CrossOrigin(origins = "*")
public class ExamenMedicalController {

    private final ExamenMedicalService examenMedicalService;

    public ExamenMedicalController(ExamenMedicalService examenMedicalService) {
        this.examenMedicalService = examenMedicalService;
    }

    @PostMapping
    public ResponseEntity<?> prescrireExamen(@RequestBody CreateExamenMedicalRequest request) {
        try {
            return ResponseEntity.ok(examenMedicalService.prescrireExamen(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/patient/{email:.+}")
    public ResponseEntity<?> getExamensParPatient(@PathVariable String email) {
        try {
            return ResponseEntity.ok(examenMedicalService.getExamensParPatient(email));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/medecin/{email:.+}")
    public ResponseEntity<?> getExamensParMedecin(@PathVariable String email) {
        try {
            return ResponseEntity.ok(examenMedicalService.getExamensParMedecin(email));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}