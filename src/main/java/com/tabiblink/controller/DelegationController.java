package com.tabiblink.controller;

import com.tabiblink.entity.Delegation;
import com.tabiblink.service.DelegationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delegations")
@CrossOrigin(origins = "*")
public class DelegationController {

    private final DelegationService delegationService;

    public DelegationController(DelegationService delegationService) {
        this.delegationService = delegationService;
    }

    @GetMapping
    public List<Delegation> getAllDelegations() {
        return delegationService.getAllDelegations();
    }

    @GetMapping("/ville/{villeId}")
    public List<Delegation> getDelegationsByVille(@PathVariable Long villeId) {
        return delegationService.getDelegationsByVille(villeId);
    }

    @PostMapping
    public Delegation ajouterDelegation(@RequestBody Delegation delegation) {
        return delegationService.ajouterDelegation(delegation);
    }

    @PutMapping("/{id}")
    public Delegation modifierDelegation(
            @PathVariable Long id,
            @RequestBody Delegation delegation
    ) {
        return delegationService.modifierDelegation(id, delegation);
    }

    @DeleteMapping("/{id}")
    public void supprimerDelegation(@PathVariable Long id) {
        delegationService.supprimerDelegation(id);
    }
}