package com.tabiblink.entity;

import jakarta.persistence.*;

@Entity
public class Delegation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomDelegation;

    @ManyToOne
    @JoinColumn(name = "ville_id")
    private Ville ville;

    public Delegation() {
    }

    public Delegation(String nomDelegation, Ville ville) {
        this.nomDelegation = nomDelegation;
        this.ville = ville;
    }

    public Long getId() {
        return id;
    }

    public String getNomDelegation() {
        return nomDelegation;
    }

    public void setNomDelegation(String nomDelegation) {
        this.nomDelegation = nomDelegation;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }
}