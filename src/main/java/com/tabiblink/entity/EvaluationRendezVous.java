package com.tabiblink.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class EvaluationRendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer noteGlobale;

    private Integer ponctualite;

    private Integer organisation;

    private Integer clarteInformations;

    @Column(length = 1000)
    private String commentaire;

    private LocalDateTime dateEvaluation = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "rendez_vous_id", nullable = false, unique = true)
    private RendezVous rendezVous;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_id", nullable = false)
    private Medecin medecin;
}