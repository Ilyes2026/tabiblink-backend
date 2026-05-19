package com.tabiblink.entity;

import jakarta.persistence.*;

@Entity
public class ParametreScoreFiabilite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer scoreInitial = 100;

    private Integer penaliteAnnulationTardive = 20;

    private Integer penaliteAbsence = 30;

    private Integer heuresAnnulationTardive = 24;

    private Integer seuilTresFiable = 80;

    private Integer seuilFiable = 60;

    private Integer seuilMoyen = 40;

    public ParametreScoreFiabilite() {
    }

    public Long getId() {
        return id;
    }

    public Integer getScoreInitial() {
        return scoreInitial;
    }

    public void setScoreInitial(Integer scoreInitial) {
        this.scoreInitial = scoreInitial;
    }

    public Integer getPenaliteAnnulationTardive() {
        return penaliteAnnulationTardive;
    }

    public void setPenaliteAnnulationTardive(Integer penaliteAnnulationTardive) {
        this.penaliteAnnulationTardive = penaliteAnnulationTardive;
    }

    public Integer getPenaliteAbsence() {
        return penaliteAbsence;
    }

    public void setPenaliteAbsence(Integer penaliteAbsence) {
        this.penaliteAbsence = penaliteAbsence;
    }

    public Integer getHeuresAnnulationTardive() {
        return heuresAnnulationTardive;
    }

    public void setHeuresAnnulationTardive(Integer heuresAnnulationTardive) {
        this.heuresAnnulationTardive = heuresAnnulationTardive;
    }

    public Integer getSeuilTresFiable() {
        return seuilTresFiable;
    }

    public void setSeuilTresFiable(Integer seuilTresFiable) {
        this.seuilTresFiable = seuilTresFiable;
    }

    public Integer getSeuilFiable() {
        return seuilFiable;
    }

    public void setSeuilFiable(Integer seuilFiable) {
        this.seuilFiable = seuilFiable;
    }

    public Integer getSeuilMoyen() {
        return seuilMoyen;
    }

    public void setSeuilMoyen(Integer seuilMoyen) {
        this.seuilMoyen = seuilMoyen;
    }
}