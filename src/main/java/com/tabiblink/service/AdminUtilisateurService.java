package com.tabiblink.service;

import com.tabiblink.dto.AdminUtilisateurResponse;
import com.tabiblink.entity.Medecin;
import com.tabiblink.entity.Role;
import com.tabiblink.entity.StatutCompte;
import com.tabiblink.entity.Utilisateur;
import com.tabiblink.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public AdminUtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<AdminUtilisateurResponse> getTousLesUtilisateurs() {
        return utilisateurRepository.findAll()
                .stream()
                .map(AdminUtilisateurResponse::fromEntity)
                .toList();
    }

    public List<AdminUtilisateurResponse> getPatients() {
        return utilisateurRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == Role.PATIENT)
                .map(AdminUtilisateurResponse::fromEntity)
                .toList();
    }

    public List<AdminUtilisateurResponse> getMedecins() {
        return utilisateurRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == Role.MEDECIN)
                .map(AdminUtilisateurResponse::fromEntity)
                .toList();
    }

    public String desactiverCompte(Long id) {
        Utilisateur utilisateur = getUtilisateur(id);
        utilisateur.setCompteActif(false);
        utilisateurRepository.save(utilisateur);

        return "Compte désactivé avec succès.";
    }

    public String reactiverCompte(Long id) {
        Utilisateur utilisateur = getUtilisateur(id);
        utilisateur.setCompteActif(true);
        utilisateurRepository.save(utilisateur);

        return "Compte réactivé avec succès.";
    }

    public String validerMedecin(Long id) {
        Utilisateur utilisateur = getUtilisateur(id);

        if (!(utilisateur instanceof Medecin medecin)) {
            throw new RuntimeException("Cet utilisateur n'est pas un médecin.");
        }

        medecin.setStatutCompte(StatutCompte.VALIDE);
        medecin.setCompteActif(true);

        utilisateurRepository.save(medecin);

        return "Médecin validé avec succès.";
    }
    public String refuserMedecin(Long id) {
        Utilisateur utilisateur = getUtilisateur(id);

        if (!(utilisateur instanceof Medecin medecin)) {
            throw new RuntimeException("Cet utilisateur n'est pas un médecin.");
        }

        medecin.setStatutCompte(StatutCompte.REFUSE);
        medecin.setCompteActif(false);

        utilisateurRepository.save(medecin);

        return "Médecin refusé.";
    }

    public String supprimerCompte(Long id) {
        Utilisateur utilisateur = getUtilisateur(id);

        if (utilisateur.getRole() == Role.ADMINISTRATEUR) {
            throw new RuntimeException("Impossible de supprimer un administrateur.");
        }

        utilisateur.setCompteActif(false);
        utilisateurRepository.save(utilisateur);

        return "Compte désactivé (historique conservé).";
    }

    private Utilisateur getUtilisateur(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));
    }
}