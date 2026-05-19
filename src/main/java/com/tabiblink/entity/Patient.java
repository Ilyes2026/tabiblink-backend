package com.tabiblink.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends Utilisateur {

    private String telephone;
    private LocalDate dateNaissance;
    private String adresse;
    private String sexe;

    private Boolean emailVerifie = false;
    private String codeVerificationEmail;
    private LocalDateTime codeVerificationExpireAt;
}