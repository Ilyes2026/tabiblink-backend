package com.tabiblink.repository;

import com.tabiblink.entity.Medecin;
import com.tabiblink.entity.StatutCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {

    boolean existsByMatricule(String matricule);

    Optional<Medecin> findByEmail(String email);
    List<Medecin> findByVilleId(Long villeId);

    List<Medecin> findByDelegationId(Long delegationId);
    List<Medecin> findBySpecialiteId(Long specialiteId);
    long countByStatutCompte(StatutCompte statutCompte);
    @Query("""
    SELECT m FROM Medecin m
    LEFT JOIN m.specialite s
    LEFT JOIN m.ville v
    LEFT JOIN m.delegation d
    WHERE m.statutCompte = 'VALIDE'
      AND (
            :nom IS NULL OR :nom = ''
            OR LOWER(m.nom) LIKE LOWER(CONCAT('%', :nom, '%'))
            OR LOWER(m.prenom) LIKE LOWER(CONCAT('%', :nom, '%'))
            OR LOWER(CONCAT(m.nom, ' ', m.prenom)) LIKE LOWER(CONCAT('%', :nom, '%'))
            OR LOWER(CONCAT(m.prenom, ' ', m.nom)) LIKE LOWER(CONCAT('%', :nom, '%'))
          )
      AND (
            :specialite IS NULL OR :specialite = ''
            OR LOWER(s.nomFr) LIKE LOWER(CONCAT('%', :specialite, '%'))
            OR LOWER(s.nomAr) LIKE LOWER(CONCAT('%', :specialite, '%'))
          )
      AND (
            :ville IS NULL OR :ville = ''
            OR LOWER(v.nomVille) LIKE LOWER(CONCAT('%', :ville, '%'))
          )
      AND (
            :delegation IS NULL OR :delegation = ''
            OR LOWER(d.nomDelegation) LIKE LOWER(CONCAT('%', :delegation, '%'))
          )
    ORDER BY m.nom ASC, m.prenom ASC
    """)
    List<Medecin> rechercherMedecins(
            @Param("nom") String nom,
            @Param("specialite") String specialite,
            @Param("ville") String ville,
            @Param("delegation") String delegation
    );
}