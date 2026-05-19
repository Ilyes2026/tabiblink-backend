package com.tabiblink.entity;

import jakarta.persistence.*;

@Entity
public class Ville {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomVille;

    public Ville() {
    }

    public Ville(String nomVille) {
        this.nomVille = nomVille;
    }

    public Long getId() {
        return id;
    }

    public String getNomVille() {
        return nomVille;
    }

    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
    }
}