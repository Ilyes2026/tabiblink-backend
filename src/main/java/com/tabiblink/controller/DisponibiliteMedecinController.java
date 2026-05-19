package com.tabiblink.controller;

import com.tabiblink.dto.DisponibiliteMedecinRequest;
import com.tabiblink.dto.DisponibiliteMedecinResponse;
import com.tabiblink.service.DisponibiliteMedecinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilites-medecin")
@CrossOrigin("*")
public class DisponibiliteMedecinController {

    private final DisponibiliteMedecinService disponibiliteService;

    public DisponibiliteMedecinController(DisponibiliteMedecinService disponibiliteService) {
        this.disponibiliteService = disponibiliteService;
    }

    @PostMapping
    public ResponseEntity<?> ajouterOuMettreAJour(@RequestBody DisponibiliteMedecinRequest request) {
        try {
            DisponibiliteMedecinResponse response = disponibiliteService.ajouterOuMettreAJour(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{medecinId}")
    public ResponseEntity<List<DisponibiliteMedecinResponse>> getDisponibilites(@PathVariable Long medecinId) {
        return ResponseEntity.ok(disponibiliteService.getDisponibilitesMedecin(medecinId));
    }

    @GetMapping("/{medecinId}/dates-disponibles")
    public ResponseEntity<List<String>> getDatesDisponibles(@PathVariable Long medecinId) {
        return ResponseEntity.ok(disponibiliteService.getDatesDisponibles(medecinId));
    }

    @GetMapping("/{medecinId}/heures-disponibles")
    public ResponseEntity<?> getHeuresDisponibles(
            @PathVariable Long medecinId,
            @RequestParam String date
    ) {
        try {
            return ResponseEntity.ok(disponibiliteService.getHeuresDisponibles(medecinId, date));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}