package com.tabiblink.controller;

import com.tabiblink.dto.MedecinRechercheResponse;
import com.tabiblink.dto.UpdateMedecinProfilRequest;
import com.tabiblink.service.MedecinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@RestController
@RequestMapping("/api/medecins")
@CrossOrigin("*")
public class MedecinController {

    private final MedecinService medecinService;

    public MedecinController(MedecinService medecinService) {
        this.medecinService = medecinService;
    }

    @GetMapping("/recherche")
    public List<MedecinRechercheResponse> rechercherMedecins(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String specialite,
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) String delegation
    ) {
        return medecinService.rechercherMedecins(nom, specialite, ville, delegation);
    }

    @GetMapping("/profil/{email}")
    public ResponseEntity<?> getProfilMedecin(@PathVariable String email) {
        try {
            return ResponseEntity.ok(medecinService.getProfilMedecin(email));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/profil/{email}")
    public ResponseEntity<?> updateProfilMedecin(
            @PathVariable String email,
            @RequestBody UpdateMedecinProfilRequest request
    ) {
        try {
            return ResponseEntity.ok(
                    medecinService.updateProfilMedecin(email, request)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/par-patient/{email:.+}")
    public ResponseEntity<?> getMedecinsParPatient(@PathVariable String email) {
        try {
            return ResponseEntity.ok(medecinService.getMedecinsParPatient(email));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}