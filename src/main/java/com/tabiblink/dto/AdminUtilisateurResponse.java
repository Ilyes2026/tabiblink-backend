package com.tabiblink.dto;

import com.tabiblink.entity.Medecin;
import com.tabiblink.entity.Patient;
import com.tabiblink.entity.Role;
import com.tabiblink.entity.Utilisateur;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AdminUtilisateurResponse {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Role role;
    private Boolean compteActif;
    private LocalDateTime dateCreation;

    private String telephone;
    private String sexe;
    private LocalDate dateNaissance;
    private String adresse;

    private String matricule;
    private String statutCompte;
    private String specialite;
    private String ville;
    private String delegation;

    private String titre;
    private String telephoneFixe;
    private String telephoneMobile;
    private String adresseCabinet;
    private String bio;
    private Boolean conventionneCnam;
    private Integer dureeConsultation;
    private String diplomesFormations;
    private String photoIdentite;
    private String carteVisite;
    private String carteProfessionnelle;
    private Double latitude;
    private Double longitude;

    public static AdminUtilisateurResponse fromEntity(Utilisateur utilisateur) {
        AdminUtilisateurResponse response = new AdminUtilisateurResponse();

        response.setId(utilisateur.getId());
        response.setNom(utilisateur.getNom());
        response.setPrenom(utilisateur.getPrenom());
        response.setEmail(utilisateur.getEmail());
        response.setRole(utilisateur.getRole());
        response.setCompteActif(utilisateur.getCompteActif());
        response.setDateCreation(utilisateur.getDateCreation());

        if (utilisateur instanceof Patient patient) {
            response.setTelephone(patient.getTelephone());
            response.setSexe(patient.getSexe());
            response.setDateNaissance(patient.getDateNaissance());
            response.setAdresse(patient.getAdresse());
        }

        if (utilisateur instanceof Medecin medecin) {
            response.setTelephone(
                    medecin.getTelephoneMobile() != null && !medecin.getTelephoneMobile().isBlank()
                            ? medecin.getTelephoneMobile()
                            : medecin.getTelephoneFixe()
            );

            response.setMatricule(medecin.getMatricule());
            response.setStatutCompte(
                    medecin.getStatutCompte() != null
                            ? medecin.getStatutCompte().name()
                            : "EN_ATTENTE"
            );

            response.setTitre(medecin.getTitre());
            response.setTelephoneFixe(medecin.getTelephoneFixe());
            response.setTelephoneMobile(medecin.getTelephoneMobile());
            response.setAdresseCabinet(medecin.getAdresseCabinet());
            response.setBio(medecin.getBio());
            response.setConventionneCnam(medecin.getConventionneCnam());
            response.setDureeConsultation(medecin.getDureeConsultation());
            response.setDiplomesFormations(medecin.getDiplomesFormations());
            response.setPhotoIdentite(medecin.getPhotoIdentite());
            response.setCarteVisite(medecin.getCarteVisite());
            response.setCarteProfessionnelle(medecin.getCarteProfessionnelle());
            response.setLatitude(medecin.getLatitude());
            response.setLongitude(medecin.getLongitude());

            if (medecin.getSpecialite() != null) {
                response.setSpecialite(medecin.getSpecialite().getNomFr());
            }

            if (medecin.getVille() != null) {
                response.setVille(medecin.getVille().getNomVille());
            }

            if (medecin.getDelegation() != null) {
                response.setDelegation(medecin.getDelegation().getNomDelegation());
            }
        }

        return response;
    }
}