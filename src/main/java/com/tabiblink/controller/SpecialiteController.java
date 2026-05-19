package com.tabiblink.controller;

import com.tabiblink.entity.Specialite;
import com.tabiblink.service.SpecialiteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialites")
@CrossOrigin("*")
public class SpecialiteController {

    private final SpecialiteService specialiteService;

    public SpecialiteController(SpecialiteService specialiteService) {
        this.specialiteService = specialiteService;
    }

    @GetMapping
    public List<Specialite> getAllSpecialites() {
        return specialiteService.getAllSpecialites();
    }

    @PostMapping
    public Specialite ajouterSpecialite(@RequestBody Specialite specialite) {
        return specialiteService.ajouterSpecialite(specialite);
    }

    @PutMapping("/{id}")
    public Specialite modifierSpecialite(
            @PathVariable Long id,
            @RequestBody Specialite specialite
    ) {
        return specialiteService.modifierSpecialite(id, specialite);
    }

    @DeleteMapping("/{id}")
    public String supprimerSpecialite(@PathVariable Long id) {
        return specialiteService.supprimerSpecialite(id);
    }


}