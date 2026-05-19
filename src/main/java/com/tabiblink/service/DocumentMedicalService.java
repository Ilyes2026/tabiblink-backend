package com.tabiblink.service;

import com.tabiblink.dto.CreateDocumentMedicalRequest;
import com.tabiblink.dto.DocumentMedicalResponse;
import com.tabiblink.entity.DocumentMedical;
import com.tabiblink.entity.Medecin;
import com.tabiblink.entity.Patient;
import com.tabiblink.repository.DocumentMedicalRepository;
import com.tabiblink.repository.MedecinRepository;
import com.tabiblink.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
public class DocumentMedicalService {

    private final DocumentMedicalRepository documentMedicalRepository;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;

    public DocumentMedicalService(DocumentMedicalRepository documentMedicalRepository,
                                  PatientRepository patientRepository,
                                  MedecinRepository medecinRepository) {
        this.documentMedicalRepository = documentMedicalRepository;
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
    }

    public DocumentMedicalResponse ajouterDocument(CreateDocumentMedicalRequest request) {
        Patient patient = patientRepository.findByEmail(request.getPatientEmail())
                .orElseThrow(() -> new RuntimeException("Patient introuvable."));

        DocumentMedical document = new DocumentMedical();
        document.setPatient(patient);
        document.setTitre(request.getTitre());
        document.setTypeDocument(request.getTypeDocument());
        document.setCheminFichier(request.getCheminFichier());
        document.setDateAjout(LocalDateTime.now());
        document.setAutorise(false);
        document.setMedecinAutorise(null);

        DocumentMedical saved = documentMedicalRepository.save(document);
        return toResponse(saved);
    }

    public List<DocumentMedicalResponse> getDocumentsParPatient(String patientEmail) {
        return documentMedicalRepository
                .findByPatientEmailOrderByDateAjoutDesc(patientEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public DocumentMedicalResponse autoriserDocument(Long documentId, Long medecinId) {
        DocumentMedical document = documentMedicalRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document introuvable."));

        Medecin medecin = medecinRepository.findById(medecinId)
                .orElseThrow(() -> new RuntimeException("Médecin introuvable."));

        document.setAutorise(true);
        document.setMedecinAutorise(medecin);

        DocumentMedical saved = documentMedicalRepository.save(document);
        return toResponse(saved);
    }

    public List<DocumentMedicalResponse> getDocumentsAutorisesPourMedecin(
            String medecinEmail,
            String patientEmail
    ) {
        return documentMedicalRepository
                .findByPatientEmailAndMedecinAutoriseEmailAndAutoriseTrueOrderByDateAjoutDesc(
                        patientEmail,
                        medecinEmail
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public DocumentMedicalResponse ajouterDocumentAvecFichier(
            String patientEmail,
            String titre,
            String typeDocument,
            MultipartFile fichier
    ) {
        Patient patient = patientRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new RuntimeException("Patient introuvable."));

        if (fichier == null || fichier.isEmpty()) {
            throw new RuntimeException("Veuillez sélectionner un fichier.");
        }

        try {
            Path dossierUpload = Paths.get("uploads", "documents")
                    .toAbsolutePath()
                    .normalize();

            Files.createDirectories(dossierUpload);

            String nomOriginal = fichier.getOriginalFilename();
            String extension = "";

            if (nomOriginal != null && nomOriginal.contains(".")) {
                extension = nomOriginal.substring(nomOriginal.lastIndexOf("."));
            }

            String nomFichier = UUID.randomUUID() + extension;
            Path cheminFichier = dossierUpload.resolve(nomFichier);

            Files.copy(fichier.getInputStream(), cheminFichier);

            String cheminPourBase = "uploads/documents/" + nomFichier;

            DocumentMedical document = new DocumentMedical();
            document.setPatient(patient);
            document.setTitre(titre);
            document.setTypeDocument(typeDocument);
            document.setCheminFichier(cheminPourBase);
            document.setDateAjout(LocalDateTime.now());
            document.setAutorise(false);
            document.setMedecinAutorise(null);

            DocumentMedical saved = documentMedicalRepository.save(document);
            return toResponse(saved);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier : " + e.getMessage());
        }
    }

    private DocumentMedicalResponse toResponse(DocumentMedical document) {
        String patientNomComplet = "";
        if (document.getPatient() != null) {
            patientNomComplet =
                    document.getPatient().getNom() + " " + document.getPatient().getPrenom();
        }

        String medecinNomComplet = "";
        if (document.getMedecinAutorise() != null) {
            medecinNomComplet =
                    document.getMedecinAutorise().getNom() + " " + document.getMedecinAutorise().getPrenom();
        }

        return new DocumentMedicalResponse(
                document.getId(),
                document.getTitre(),
                document.getTypeDocument(),
                document.getCheminFichier(),
                document.getDateAjout() != null ? document.getDateAjout().toString() : "",
                document.isAutorise(),
                patientNomComplet,
                medecinNomComplet
        );
    }
    public Resource chargerFichierDocument(Long documentId) {
        try {
            DocumentMedical document = documentMedicalRepository.findById(documentId)
                    .orElseThrow(() -> new RuntimeException("Document introuvable."));

            if (document.getCheminFichier() == null || document.getCheminFichier().isBlank()) {
                throw new RuntimeException("Fichier introuvable.");
            }

            Path chemin = Paths.get(document.getCheminFichier())
                    .toAbsolutePath()
                    .normalize();

            Resource resource = new UrlResource(chemin.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Fichier non lisible.");
            }

            return resource;

        } catch (Exception e) {
            throw new RuntimeException("Impossible d’ouvrir le fichier : " + e.getMessage());
        }
    }

    public String getContentTypeDocument(Long documentId) {
        try {
            DocumentMedical document = documentMedicalRepository.findById(documentId)
                    .orElseThrow(() -> new RuntimeException("Document introuvable."));

            Path chemin = Paths.get(document.getCheminFichier())
                    .toAbsolutePath()
                    .normalize();

            String contentType = Files.probeContentType(chemin);

            return contentType != null ? contentType : "application/octet-stream";

        } catch (Exception e) {
            return "application/octet-stream";
        }
    }
}