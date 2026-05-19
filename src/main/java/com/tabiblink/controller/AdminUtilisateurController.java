package com.tabiblink.controller;

import com.tabiblink.dto.AdminUtilisateurResponse;
import com.tabiblink.service.AdminUtilisateurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/utilisateurs")
@CrossOrigin("*")
public class AdminUtilisateurController {

    private final AdminUtilisateurService adminUtilisateurService;

    public AdminUtilisateurController(AdminUtilisateurService adminUtilisateurService) {
        this.adminUtilisateurService = adminUtilisateurService;
    }

    @GetMapping
    public List<AdminUtilisateurResponse> getTousLesUtilisateurs() {
        return adminUtilisateurService.getTousLesUtilisateurs();
    }

    @GetMapping("/patients")
    public List<AdminUtilisateurResponse> getPatients() {
        return adminUtilisateurService.getPatients();
    }

    @GetMapping("/medecins")
    public List<AdminUtilisateurResponse> getMedecins() {
        return adminUtilisateurService.getMedecins();
    }

    @PutMapping("/{id}/desactiver")
    public String desactiverCompte(@PathVariable Long id) {
        return adminUtilisateurService.desactiverCompte(id);
    }

    @PutMapping("/{id}/reactiver")
    public String reactiverCompte(@PathVariable Long id) {
        return adminUtilisateurService.reactiverCompte(id);
    }

    @PutMapping("/medecins/{id}/valider")
    public String validerMedecin(@PathVariable Long id) {
        return adminUtilisateurService.validerMedecin(id);
    }

    @PutMapping("/medecins/{id}/refuser")
    public String refuserMedecin(@PathVariable Long id) {
        return adminUtilisateurService.refuserMedecin(id);
    }

    @DeleteMapping("/{id}")
    public String supprimerCompte(@PathVariable Long id) {
        return adminUtilisateurService.supprimerCompte(id);
    }
}