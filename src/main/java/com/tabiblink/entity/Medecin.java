package com.tabiblink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medecin extends Utilisateur {

    private String matricule;
    private String adresseCabinet;
    private String bio;

    private String titre;
    private String telephoneFixe;
    private String telephoneMobile;
    private Boolean conventionneCnam;
    private Integer dureeConsultation;
    private String diplomesFormations;
    private String photoIdentite;
    private String carteVisite;
    private String carteProfessionnelle;
    @Enumerated(EnumType.STRING)
    private StatutCompte statutCompte = StatutCompte.EN_ATTENTE;

    private Double latitude;
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "specialite_id")
    private Specialite specialite;

    @ManyToOne
    @JoinColumn(name = "ville_id")
    private Ville ville;

    @ManyToOne
    @JoinColumn(name = "delegation_id")
    private Delegation delegation;
}