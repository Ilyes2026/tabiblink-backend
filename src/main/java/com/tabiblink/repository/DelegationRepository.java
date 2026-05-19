package com.tabiblink.repository;

import com.tabiblink.entity.Delegation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DelegationRepository extends JpaRepository<Delegation, Long> {
    List<Delegation> findByVilleId(Long villeId);
}