package com.tabiblink.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RappelRendezVousScheduler {

    private final RendezVousService rendezVousService;

    public RappelRendezVousScheduler(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @Scheduled(cron = "0 0 8 * * *", zone = "Africa/Tunis")
    public void envoyerRappelsChaqueJour() {
        int nombreEnvoyes = rendezVousService.envoyerRappelsRendezVousDemain();

        System.out.println("Rappels de rendez-vous envoyés automatiquement : " + nombreEnvoyes);
    }
}