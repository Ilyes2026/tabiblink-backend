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
public class DelegationService {

    private final DelegationRepository delegationRepository;
    private final VilleRepository villeRepository;
    private final MedecinRepository medecinRepository;

    public DelegationService(
            DelegationRepository delegationRepository,
            VilleRepository villeRepository,
            MedecinRepository medecinRepository
    ) {
        this.delegationRepository = delegationRepository;
        this.villeRepository = villeRepository;
        this.medecinRepository = medecinRepository;
    }

    public List<Delegation> getAllDelegations() {
        return delegationRepository.findAll();
    }

    public List<Delegation> getDelegationsByVille(Long villeId) {
        return delegationRepository.findByVilleId(villeId);
    }

    public Delegation ajouterDelegation(Delegation delegation) {
        Long villeId = delegation.getVille().getId();

        Ville ville = villeRepository.findById(villeId)
                .orElseThrow(() -> new RuntimeException("Ville introuvable"));

        delegation.setVille(ville);

        return delegationRepository.save(delegation);
    }

    public Delegation modifierDelegation(Long id, Delegation delegationDetails) {
        Delegation delegation = delegationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Délégation introuvable"));

        delegation.setNomDelegation(delegationDetails.getNomDelegation());

        Long villeId = delegationDetails.getVille().getId();

        Ville ville = villeRepository.findById(villeId)
                .orElseThrow(() -> new RuntimeException("Ville introuvable"));

        delegation.setVille(ville);

        return delegationRepository.save(delegation);
    }

    @Transactional
    public void supprimerDelegation(Long id) {
        List<Medecin> medecins = medecinRepository.findByDelegationId(id);

        for (Medecin medecin : medecins) {
            medecin.setDelegation(null);
        }

        medecinRepository.saveAll(medecins);

        delegationRepository.deleteById(id);
    }
}