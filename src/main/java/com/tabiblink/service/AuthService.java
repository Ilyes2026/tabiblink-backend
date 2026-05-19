package com.tabiblink.service;

import com.tabiblink.dto.InscriptionMedecinRequest;
import com.tabiblink.dto.InscriptionPatientRequest;
import com.tabiblink.dto.LoginRequest;
import com.tabiblink.dto.LoginResponse;
import com.tabiblink.entity.*;
import com.tabiblink.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.Optional;


@Service
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final SpecialiteRepository specialiteRepository;
    private final VilleRepository villeRepository;
    private final DelegationRepository delegationRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UtilisateurRepository utilisateurRepository,
                       PatientRepository patientRepository,
                       MedecinRepository medecinRepository,
                       SpecialiteRepository specialiteRepository,
                       VilleRepository villeRepository,
                       DelegationRepository delegationRepository,
                       PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.specialiteRepository = specialiteRepository;
        this.villeRepository = villeRepository;
        this.delegationRepository = delegationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String inscrirePatient(InscriptionPatientRequest request) {
        String email = request.getEmail().trim();

        if (utilisateurRepository.existsByEmail(email)) {
            return "Cet email existe déjà.";
        }

        Patient patient = new Patient();
        patient.setNom(request.getNom());
        patient.setPrenom(request.getPrenom());
        patient.setEmail(email);
        patient.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        patient.setRole(Role.PATIENT);
        patient.setDateCreation(LocalDateTime.now());
        patient.setCompteActif(true);
        patient.setTelephone(request.getTelephone());
        patient.setDateNaissance(request.getDateNaissance());
        patient.setAdresse(request.getAdresse());
        patient.setSexe(request.getSexe());

        patientRepository.save(patient);

        return "Inscription patient réussie.";
    }

    public String inscrireMedecin(InscriptionMedecinRequest request) {
        String email = request.getEmail().trim();

        Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            Utilisateur user = userOpt.get();

            if (user instanceof Medecin) {
                Medecin medecin = (Medecin) user;

                if (medecin.getStatutCompte() == StatutCompte.REFUSE) {

                    medecinRepository.delete(medecin);
                } else {
                    return "Cet email est déjà utilisé.";
                }

            } else {

                return "Cet email est déjà utilisé.";
            }
        }

        if (medecinRepository.existsByMatricule(request.getMatricule())) {
            return "Ce matricule existe déjà.";
        }

        Specialite specialite = specialiteRepository.findById(request.getSpecialiteId())
                .orElseThrow(() -> new RuntimeException("Spécialité introuvable."));

        Ville ville = villeRepository.findById(request.getVilleId())
                .orElseThrow(() -> new RuntimeException("Ville introuvable."));

        Delegation delegation = delegationRepository.findById(request.getDelegationId())
                .orElseThrow(() -> new RuntimeException("Délégation introuvable."));

        Medecin medecin = new Medecin();
        medecin.setNom(request.getNom());
        medecin.setPrenom(request.getPrenom());
        medecin.setEmail(email);
        medecin.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        medecin.setRole(Role.MEDECIN);
        medecin.setDateCreation(LocalDateTime.now());
        medecin.setCompteActif(true);

        medecin.setMatricule(request.getMatricule());
        medecin.setAdresseCabinet(request.getAdresseCabinet());
        medecin.setBio(request.getBio());
        medecin.setSpecialite(specialite);
        medecin.setVille(ville);
        medecin.setDelegation(delegation);
        medecin.setTitre(request.getTitre());
        medecin.setTelephoneFixe(request.getTelephoneFixe());
        medecin.setTelephoneMobile(request.getTelephoneMobile());

        medecin.setConventionneCnam(
                request.getConventionneCnam() != null ? request.getConventionneCnam() : false
        );

        medecin.setDureeConsultation(request.getDureeConsultation());
        medecin.setDiplomesFormations(request.getDiplomesFormations());
        medecin.setLatitude(request.getLatitude());
        medecin.setLongitude(request.getLongitude());

        medecin.setPhotoIdentite(
                sauvegarderFichier(request.getPhotoIdentite(), "photo-identite")
        );
        medecin.setCarteVisite(
                sauvegarderFichier(request.getCarteVisite(), "carte-visite")
        );
        medecin.setCarteProfessionnelle(
                sauvegarderFichier(request.getCarteProfessionnelle(), "carte-professionnelle")
        );

        medecin.setStatutCompte(StatutCompte.EN_ATTENTE);

        medecinRepository.save(medecin);

        return "Inscription médecin enregistrée. Votre compte est en attente de validation par l'administrateur.";
    }

    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail().trim();

        Utilisateur utilisateur = utilisateurRepository
                .findByEmail(email)
                .orElse(null);

        if (utilisateur == null) {
            throw new RuntimeException("Email introuvable.");
        }

        if (utilisateur.getCompteActif() != null && !utilisateur.getCompteActif()) {
            throw new RuntimeException("Votre compte est désactivé.");
        }

        if (!passwordEncoder.matches(request.getMotDePasse(), utilisateur.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect.");
        }

        if (utilisateur instanceof Medecin) {
            Medecin medecin = (Medecin) utilisateur;

            if (medecin.getStatutCompte() == StatutCompte.EN_ATTENTE) {
                throw new RuntimeException("Votre compte est en attente de validation.");
            }

            if (medecin.getStatutCompte() == StatutCompte.REFUSE) {
                throw new RuntimeException("Votre compte a été refusé. Veuillez vous réinscrire.");
            }

            if (medecin.getStatutCompte() == StatutCompte.DESACTIVE) {
                throw new RuntimeException("Votre compte est désactivé.");
            }
        }

        return new LoginResponse(
                utilisateur.getId(),
                utilisateur.getEmail(),
                utilisateur.getRole().name(),
                "Connexion réussie"
        );
    }

    private String sauvegarderFichier(MultipartFile fichier, String dossier) {
        if (fichier == null || fichier.isEmpty()) {
            return null;
        }

        try {
            Path uploadPath = Paths.get("uploads", "medecins", dossier);
            Files.createDirectories(uploadPath);

            String nomOriginal = StringUtils.cleanPath(
                    Objects.requireNonNull(fichier.getOriginalFilename())
            );

            String extension = "";
            int index = nomOriginal.lastIndexOf('.');
            if (index >= 0) {
                extension = nomOriginal.substring(index);
            }

            String nomFichier = UUID.randomUUID() + extension;
            Path destination = uploadPath.resolve(nomFichier);

            Files.copy(fichier.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return destination.toString().replace("\\", "/");

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erreur lors de l'enregistrement du fichier : " + fichier.getOriginalFilename(),
                    e
            );
        }
    }
}