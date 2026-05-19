package com.tabiblink.service;

import com.tabiblink.dto.ParametreScoreFiabiliteRequest;
import com.tabiblink.dto.ParametreScoreFiabiliteResponse;
import com.tabiblink.entity.ParametreScoreFiabilite;
import com.tabiblink.repository.ParametreScoreFiabiliteRepository;
import org.springframework.stereotype.Service;

@Service
public class ParametreScoreFiabiliteService {

    private final ParametreScoreFiabiliteRepository repository;

    public ParametreScoreFiabiliteService(ParametreScoreFiabiliteRepository repository) {
        this.repository = repository;
    }

    public ParametreScoreFiabiliteResponse getParametres() {
        ParametreScoreFiabilite parametres = getOrCreateParametres();
        return toResponse(parametres);
    }

    public ParametreScoreFiabiliteResponse modifierParametres(ParametreScoreFiabiliteRequest request) {
        ParametreScoreFiabilite parametres = getOrCreateParametres();

        parametres.setScoreInitial(request.getScoreInitial());
        parametres.setPenaliteAnnulationTardive(request.getPenaliteAnnulationTardive());
        parametres.setPenaliteAbsence(request.getPenaliteAbsence());
        parametres.setHeuresAnnulationTardive(request.getHeuresAnnulationTardive());
        parametres.setSeuilTresFiable(request.getSeuilTresFiable());
        parametres.setSeuilFiable(request.getSeuilFiable());
        parametres.setSeuilMoyen(request.getSeuilMoyen());

        ParametreScoreFiabilite saved = repository.save(parametres);
        return toResponse(saved);
    }

    public ParametreScoreFiabilite getOrCreateParametres() {
        return repository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> repository.save(new ParametreScoreFiabilite()));
    }

    private ParametreScoreFiabiliteResponse toResponse(ParametreScoreFiabilite parametres) {
        ParametreScoreFiabiliteResponse response = new ParametreScoreFiabiliteResponse();

        response.setId(parametres.getId());
        response.setScoreInitial(parametres.getScoreInitial());
        response.setPenaliteAnnulationTardive(parametres.getPenaliteAnnulationTardive());
        response.setPenaliteAbsence(parametres.getPenaliteAbsence());
        response.setHeuresAnnulationTardive(parametres.getHeuresAnnulationTardive());
        response.setSeuilTresFiable(parametres.getSeuilTresFiable());
        response.setSeuilFiable(parametres.getSeuilFiable());
        response.setSeuilMoyen(parametres.getSeuilMoyen());

        return response;
    }
}