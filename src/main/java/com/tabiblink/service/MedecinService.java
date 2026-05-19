package com.tabiblink.service;

import com.tabiblink.dto.MedecinProfilResponse;
import com.tabiblink.dto.MedecinRechercheResponse;
import com.tabiblink.dto.MedecinSimpleResponse;
import com.tabiblink.dto.UpdateMedecinProfilRequest;
import com.tabiblink.entity.Delegation;
import com.tabiblink.entity.Medecin;
import com.tabiblink.entity.StatutRendezVous;
import com.tabiblink.entity.Ville;
import com.tabiblink.repository.DelegationRepository;
import com.tabiblink.repository.MedecinRepository;
import com.tabiblink.repository.RendezVousRepository;
import com.tabiblink.repository.SpecialiteRepository;
import com.tabiblink.repository.VilleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService {

    private final MedecinRepository medecinRepository;
    private final SpecialiteRepository specialiteRepository;
    private final VilleRepository villeRepository;
    private final DelegationRepository delegationRepository;
    private final RendezVousRepository rendezVousRepository;

    public MedecinService(
            MedecinRepository medecinRepository,
            SpecialiteRepository specialiteRepository,
            VilleRepository villeRepository,
            DelegationRepository delegationRepository,
            RendezVousRepository rendezVousRepository
    ) {
        this.medecinRepository = medecinRepository;
        this.specialiteRepository = specialiteRepository;
        this.villeRepository = villeRepository;
        this.delegationRepository = delegationRepository;
        this.rendezVousRepository = rendezVousRepository;
    }

    public List<MedecinRechercheResponse> rechercherMedecins(
            String nom,
            String specialite,
            String ville,
            String delegation
    ) {
        return medecinRepository.rechercherMedecins(nom, specialite, ville, delegation)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public MedecinProfilResponse getProfilMedecin(String email) {
        Medecin medecin = medecinRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Médecin introuvable."));

        return toProfilResponse(medecin);
    }

    public MedecinProfilResponse updateProfilMedecin(
            String email,
            UpdateMedecinProfilRequest request
    ) {
        Medecin medecin = medecinRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Médecin introuvable."));

        medecin.setNom(request.getNom());
        medecin.setPrenom(request.getPrenom());
        medecin.setTitre(request.getTitre());

        medecin.setTelephoneFixe(request.getTelephoneFixe());
        medecin.setTelephoneMobile(request.getTelephoneMobile());

        medecin.setAdresseCabinet(request.getAdresseCabinet());
        medecin.setBio(request.getBio());

        medecin.setConventionneCnam(request.getConventionneCnam());
        medecin.setDureeConsultation(request.getDureeConsultation());

        medecin.setDiplomesFormations(request.getDiplomesFormations());

        medecin.setLatitude(request.getLatitude());
        medecin.setLongitude(request.getLongitude());

        if (request.getVilleId() != null) {
            Ville ville = villeRepository.findById(request.getVilleId())
                    .orElseThrow(() -> new RuntimeException("Ville introuvable."));
            medecin.setVille(ville);
        }

        if (request.getDelegationId() != null) {
            Delegation delegationEntity = delegationRepository.findById(request.getDelegationId())
                    .orElseThrow(() -> new RuntimeException("Délégation introuvable."));
            medecin.setDelegation(delegationEntity);
        }

        Medecin saved = medecinRepository.save(medecin);

        return toProfilResponse(saved);
    }

    private MedecinRechercheResponse mapToResponse(Medecin medecin) {
        String nom = medecin.getNom() != null ? medecin.getNom() : "";
        String prenom = medecin.getPrenom() != null ? medecin.getPrenom() : "";
        String nomComplet = (prenom + " " + nom).trim();

        String specialiteFr = "";
        String specialiteAr = "";

        if (medecin.getSpecialite() != null) {
            specialiteFr = medecin.getSpecialite().getNomFr() != null
                    ? medecin.getSpecialite().getNomFr()
                    : "";

            specialiteAr = medecin.getSpecialite().getNomAr() != null
                    ? medecin.getSpecialite().getNomAr()
                    : "";
        }

        String villeNom = "";
        String delegationNom = "";

        if (medecin.getVille() != null) {
            villeNom = medecin.getVille().getNomVille() != null
                    ? medecin.getVille().getNomVille()
                    : "";
        }

        if (medecin.getDelegation() != null) {
            delegationNom = medecin.getDelegation().getNomDelegation() != null
                    ? medecin.getDelegation().getNomDelegation()
                    : "";
        }

        return new MedecinRechercheResponse(
                medecin.getId(),
                nom,
                prenom,
                nomComplet,
                medecin.getTitre() != null ? medecin.getTitre() : "",
                medecin.getBio() != null ? medecin.getBio() : "",
                medecin.getAdresseCabinet() != null ? medecin.getAdresseCabinet() : "",
                medecin.getTelephoneFixe() != null ? medecin.getTelephoneFixe() : "",
                medecin.getTelephoneMobile() != null ? medecin.getTelephoneMobile() : "",
                medecin.getConventionneCnam() != null ? medecin.getConventionneCnam() : false,
                medecin.getPhotoIdentite() != null ? medecin.getPhotoIdentite() : "",
                medecin.getCarteVisite() != null ? medecin.getCarteVisite() : "",
                specialiteFr,
                specialiteAr,
                villeNom,
                delegationNom,
                medecin.getDureeConsultation() != null ? medecin.getDureeConsultation() : 0,
                medecin.getDiplomesFormations() != null ? medecin.getDiplomesFormations() : "",
                medecin.getLatitude(),
                medecin.getLongitude()
        );
    }

    private MedecinProfilResponse toProfilResponse(Medecin medecin) {
        return new MedecinProfilResponse(
                medecin.getId(),
                medecin.getNom(),
                medecin.getPrenom(),
                medecin.getEmail(),

                medecin.getMatricule(),
                medecin.getTitre(),

                medecin.getTelephoneFixe(),
                medecin.getTelephoneMobile(),

                medecin.getAdresseCabinet(),
                medecin.getBio(),

                medecin.getConventionneCnam(),
                medecin.getDureeConsultation(),

                medecin.getDiplomesFormations(),

                medecin.getSpecialite() != null ? medecin.getSpecialite().getId() : null,
                medecin.getSpecialite() != null ? medecin.getSpecialite().getNomFr() : null,
                medecin.getSpecialite() != null ? medecin.getSpecialite().getNomAr() : null,

                medecin.getVille() != null ? medecin.getVille().getId() : null,
                medecin.getVille() != null ? medecin.getVille().getNomVille() : null,

                medecin.getDelegation() != null ? medecin.getDelegation().getId() : null,
                medecin.getDelegation() != null ? medecin.getDelegation().getNomDelegation() : null,

                medecin.getLatitude(),
                medecin.getLongitude(),

                medecin.getPhotoIdentite(),

                medecin.getStatutCompte() != null
                        ? medecin.getStatutCompte().name()
                        : null
        );
    }

    public List<MedecinSimpleResponse> getMedecinsParPatient(String patientEmail) {
        List<Medecin> medecins =
                rendezVousRepository.findMedecinsByPatientEmailAndStatutNotAnnule(
                        patientEmail,
                        StatutRendezVous.ANNULE
                );

        return medecins.stream()
                .map(medecin -> new MedecinSimpleResponse(
                        medecin.getId(),
                        "Dr " + medecin.getPrenom() + " " + medecin.getNom(),
                        medecin.getSpecialite() != null
                                ? medecin.getSpecialite().getNomFr()
                                : "Spécialité non précisée",
                        medecin.getVille() != null
                                ? medecin.getVille().getNomVille()
                                : "Ville non précisée"
                ))
                .toList();
    }
}