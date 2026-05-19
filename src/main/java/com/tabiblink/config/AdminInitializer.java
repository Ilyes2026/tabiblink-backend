package com.tabiblink.config;

import com.tabiblink.entity.Administrateur;
import com.tabiblink.entity.Role;
import com.tabiblink.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.default.email}")
    private String adminEmail;

    @Value("${admin.default.password}")
    private String adminPassword;

    public AdminInitializer(UtilisateurRepository utilisateurRepository,
                            PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (utilisateurRepository.findByEmail(adminEmail).isEmpty()) {
            Administrateur admin = new Administrateur();

            admin.setNom("Admin");
            admin.setPrenom("TabibLink");
            admin.setEmail(adminEmail);
            admin.setMotDePasse(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ADMINISTRATEUR);
            admin.setCompteActif(true);

            utilisateurRepository.save(admin);

            System.out.println("✅ Compte administrateur créé : " + adminEmail);
        }
    }
}