package com.tabiblink.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void envoyerCodeVerification(String destinataire, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinataire);
        message.setSubject("Code de vérification TabibLink");
        message.setText(
                "Bonjour,\n\n" +
                        "Votre code de vérification TabibLink est : " + code + "\n\n" +
                        "Ce code expire dans 10 minutes.\n\n" +
                        "Cordialement,\nTabibLink"
        );

        mailSender.send(message);
    }

    public void envoyerRappelRendezVous(
            String destinataire,
            String patientNomComplet,
            String medecinNomComplet,
            String specialite,
            String lieu,
            String date,
            String heure
    ) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(destinataire);
        message.setSubject("Rappel de votre rendez-vous médical - TabibLink");

        message.setText(
                "Bonjour " + patientNomComplet + ",\n\n" +

                        "Nous vous rappelons que vous avez un rendez-vous médical prévu demain à "
                        + heure + " avec Dr " + medecinNomComplet + ".\n\n" +

                        "Spécialité : " + specialite + "\n" +
                        "Lieu : " + lieu + "\n" +
                        "Date : " + date + "\n" +
                        "Heure : " + heure + "\n\n" +

                        "Merci de vous présenter à l’heure prévue.\n\n" +

                        "Si vous ne pouvez pas assister au rendez-vous, veuillez l’annuler ou le reprogrammer depuis votre espace patient TabibLink.\n\n" +

                        "Cordialement,\n" +
                        "L’équipe TabibLink"
        );

        mailSender.send(message);
    }
}