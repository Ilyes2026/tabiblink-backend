package com.tabiblink.controller;

import com.tabiblink.dto.InscriptionMedecinRequest;
import com.tabiblink.dto.InscriptionPatientRequest;
import com.tabiblink.dto.LoginRequest;
import com.tabiblink.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/patient/register")
    public String inscrirePatient(@RequestBody InscriptionPatientRequest request) {
        return authService.inscrirePatient(request);
    }

    @PostMapping(value = "/medecin/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String inscrireMedecin(@ModelAttribute InscriptionMedecinRequest request) {
        return authService.inscrireMedecin(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}