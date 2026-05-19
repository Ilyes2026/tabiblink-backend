package com.tabiblink.service;

import com.tabiblink.entity.Delegation;
import com.tabiblink.entity.Medecin;
import com.tabiblink.entity.Ville;
import com.tabiblink.repository.DelegationRepository;
import com.tabiblink.repository.MedecinRepository;
import com.tabiblink.repository.VilleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {

    private final VilleRepository villeRepository;
    private final DelegationRepository delegationRepository;
    private final MedecinRepository medecinRepository;

    public VilleService(
            VilleRepository villeRepository,
            DelegationRepository delegationRepository,
            MedecinRepository medecinRepository
    ) {
        this.villeRepository = villeRepository;
        this.delegationRepository = delegationRepository;
        this.medecinRepository = medecinRepository;
    }

    public List<Ville> getAllVilles() {
        return villeRepository.findAll();
    }

    public Ville ajouterVille(Ville ville) {
        return villeRepository.save(ville);
    }

    public Ville modifierVille(Long id, Ville villeDetails) {
        Ville ville = villeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ville introuvable"));

        ville.setNomVille(villeDetails.getNomVille());

        return villeRepository.save(ville);
    }

    @Transactional
    public void supprimerVille(Long id) {
        List<Medecin> medecins = medecinRepository.findByVilleId(id);

        for (Medecin medecin : medecins) {
            medecin.setVille(null);
            medecin.setDelegation(null);
        }

        medecinRepository.saveAll(medecins);

        List<Delegation> delegations = delegationRepository.findByVilleId(id);
        delegationRepository.deleteAll(delegations);

        villeRepository.deleteById(id);
    }
}