package com.tabiblink.service;

import com.tabiblink.entity.Medecin;
import com.tabiblink.entity.Specialite;
import com.tabiblink.repository.MedecinRepository;
import com.tabiblink.repository.SpecialiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialiteService {

    private final SpecialiteRepository specialiteRepository;
    private final MedecinRepository medecinRepository;

    public SpecialiteService(
            SpecialiteRepository specialiteRepository,
            MedecinRepository medecinRepository
    ) {
        this.specialiteRepository = specialiteRepository;
        this.medecinRepository = medecinRepository;
    }

    public List<Specialite> getAllSpecialites() {
        return specialiteRepository.findAll();
    }

    public Specialite ajouterSpecialite(Specialite specialite) {
        return specialiteRepository.save(specialite);
    }

    public Specialite modifierSpecialite(Long id, Specialite specialiteDetails) {
        Specialite specialite = specialiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Spécialité introuvable"));

        specialite.setNomFr(specialiteDetails.getNomFr());
        specialite.setNomAr(specialiteDetails.getNomAr());

        return specialiteRepository.save(specialite);
    }

    @Transactional
    public String supprimerSpecialite(Long id) {
        List<Medecin> medecins = medecinRepository.findBySpecialiteId(id);

        for (Medecin medecin : medecins) {
            medecin.setSpecialite(null);
        }

        medecinRepository.saveAll(medecins);

        specialiteRepository.deleteById(id);

        return "Spécialité supprimée avec succès.";
    }
}