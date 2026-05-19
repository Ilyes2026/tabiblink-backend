package com.tabiblink.controller;

import com.tabiblink.entity.Ville;
import com.tabiblink.service.VilleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/villes")
@CrossOrigin(origins = "*")
public class VilleController {

    private final VilleService villeService;

    public VilleController(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping
    public List<Ville> getAllVilles() {
        return villeService.getAllVilles();
    }

    @PostMapping
    public Ville ajouterVille(@RequestBody Ville ville) {
        return villeService.ajouterVille(ville);
    }

    @PutMapping("/{id}")
    public Ville modifierVille(@PathVariable Long id, @RequestBody Ville ville) {
        return villeService.modifierVille(id, ville);
    }

    @DeleteMapping("/{id}")
    public void supprimerVille(@PathVariable Long id) {
        villeService.supprimerVille(id);
    }
}