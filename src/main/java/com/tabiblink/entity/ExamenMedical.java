package com.tabiblink.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ExamenMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeExamen;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate datePrescription;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

    @ManyToOne
    @JoinColumn(name = "rendez_vous_id")
    private RendezVous rendezVous;

    public ExamenMedical() {
    }

    public Long getId() {
        return id;
    }

    public String getTypeExamen() {
        return typeExamen;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDatePrescription() {
        return datePrescription;
    }

    public Patient getPatient() {
        return patient;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public RendezVous getRendezVous() {
        return rendezVous;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTypeExamen(String typeExamen) {
        this.typeExamen = typeExamen;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatePrescription(LocalDate datePrescription) {
        this.datePrescription = datePrescription;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public void setRendezVous(RendezVous rendezVous) {
        this.rendezVous = rendezVous;
    }
}