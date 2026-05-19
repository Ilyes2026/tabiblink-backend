package com.tabiblink.controller;

import com.tabiblink.dto.CreateDocumentMedicalRequest;
import com.tabiblink.service.DocumentMedicalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class DocumentMedicalController {

    private final DocumentMedicalService documentMedicalService;

    public DocumentMedicalController(DocumentMedicalService documentMedicalService) {
        this.documentMedicalService = documentMedicalService;
    }

    @PostMapping
    public ResponseEntity<?> ajouterDocument(@RequestBody CreateDocumentMedicalRequest request) {
        try {
            return ResponseEntity.ok(documentMedicalService.ajouterDocument(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/patient/{email:.+}")
    public ResponseEntity<?> getDocumentsParPatient(@PathVariable String email) {
        try {
            return ResponseEntity.ok(documentMedicalService.getDocumentsParPatient(email));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{documentId}/autoriser/{medecinId}")
    public ResponseEntity<?> autoriserDocument(
            @PathVariable Long documentId,
            @PathVariable Long medecinId
    ) {
        try {
            return ResponseEntity.ok(
                    documentMedicalService.autoriserDocument(documentId, medecinId)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/autorises/medecin/{medecinEmail:.+}/patient/{patientEmail:.+}")
    public ResponseEntity<?> getDocumentsAutorisesPourMedecin(
            @PathVariable String medecinEmail,
            @PathVariable String patientEmail
    ) {
        try {
            return ResponseEntity.ok(
                    documentMedicalService.getDocumentsAutorisesPourMedecin(
                            medecinEmail,
                            patientEmail
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> ajouterDocumentAvecFichier(
            @RequestParam("patientEmail") String patientEmail,
            @RequestParam("titre") String titre,
            @RequestParam("typeDocument") String typeDocument,
            @RequestParam("fichier") MultipartFile fichier
    ) {
        try {
            return ResponseEntity.ok(
                    documentMedicalService.ajouterDocumentAvecFichier(
                            patientEmail,
                            titre,
                            typeDocument,
                            fichier
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{documentId}/fichier")
    public ResponseEntity<Resource> ouvrirDocument(@PathVariable Long documentId) {
        try {
            Resource resource = documentMedicalService.chargerFichierDocument(documentId);
            String contentType = documentMedicalService.getContentTypeDocument(documentId);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\""
                    )
                    .body(resource);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}