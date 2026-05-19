package com.tabiblink.controller;

import com.tabiblink.dto.ParametreScoreFiabiliteRequest;
import com.tabiblink.dto.ParametreScoreFiabiliteResponse;
import com.tabiblink.service.ParametreScoreFiabiliteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/parametres-score")
@CrossOrigin(origins = "*")
public class ParametreScoreFiabiliteController {

    private final ParametreScoreFiabiliteService service;

    public ParametreScoreFiabiliteController(ParametreScoreFiabiliteService service) {
        this.service = service;
    }

    @GetMapping
    public ParametreScoreFiabiliteResponse getParametres() {
        return service.getParametres();
    }

    @PutMapping
    public ParametreScoreFiabiliteResponse modifierParametres(
            @RequestBody ParametreScoreFiabiliteRequest request
    ) {
        return service.modifierParametres(request);
    }
}