package com.tabiblink.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String brevoApiKey;

    public void envoyerCodeVerification(String destinataire, String code) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", brevoApiKey);

        Map<String, Object> body = new HashMap<>();

        Map<String, String> sender = new HashMap<>();
        sender.put("name", "TabibLink");
        sender.put("email", "tabiblink.app@gmail.com");

        body.put("sender", sender);

        body.put("to", new Object[]{
                Map.of("email", destinataire)
        });

        body.put("subject", "Code de vérification TabibLink");

        body.put(
                "htmlContent",
                "<h2>TabibLink</h2>"
                        + "<p>Votre code de vérification est :</p>"
                        + "<h1>" + code + "</h1>"
                        + "<p>Ce code expire dans 10 minutes.</p>"
        );

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                request,
                String.class
        );

        System.out.println("BREVO RESPONSE: " + response.getBody());
    }
}