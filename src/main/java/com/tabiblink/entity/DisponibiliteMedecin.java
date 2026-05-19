package com.tabiblink.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class DisponibiliteMedecin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private JourSemaine jourSemaine;

    private LocalTime heureDebutMatin;
    private LocalTime heureFinMatin;

    private LocalTime heureDebutApresMidi;
    private LocalTime heureFinApresMidi;

    private Boolean actif = true;

    @ManyToOne
    @JoinColumn(name = "medecin_id", nullable = false)
    private Medecin medecin;

    public DisponibiliteMedecin() {
    }

    public Long getId() {
        return id;
    }

    public JourSemaine getJourSemaine() {
        return jourSemaine;
    }

    public void setJourSemaine(JourSemaine jourSemaine) {
        this.jourSemaine = jourSemaine;
    }

    public LocalTime getHeureDebutMatin() {
        return heureDebutMatin;
    }

    public void setHeureDebutMatin(LocalTime heureDebutMatin) {
        this.heureDebutMatin = heureDebutMatin;
    }

    public LocalTime getHeureFinMatin() {
        return heureFinMatin;
    }

    public void setHeureFinMatin(LocalTime heureFinMatin) {
        this.heureFinMatin = heureFinMatin;
    }

    public LocalTime getHeureDebutApresMidi() {
        return heureDebutApresMidi;
    }

    public void setHeureDebutApresMidi(LocalTime heureDebutApresMidi) {
        this.heureDebutApresMidi = heureDebutApresMidi;
    }

    public LocalTime getHeureFinApresMidi() {
        return heureFinApresMidi;
    }

    public void setHeureFinApresMidi(LocalTime heureFinApresMidi) {
        this.heureFinApresMidi = heureFinApresMidi;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }
}