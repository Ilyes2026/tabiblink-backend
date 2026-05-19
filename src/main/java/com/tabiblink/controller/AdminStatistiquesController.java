package com.tabiblink.controller;

import com.tabiblink.dto.AdminStatistiquesResponse;
import com.tabiblink.service.AdminStatistiquesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/statistiques")
@CrossOrigin(origins = "*")
public class AdminStatistiquesController {

    private final AdminStatistiquesService adminStatistiquesService;

    public AdminStatistiquesController(AdminStatistiquesService adminStatistiquesService) {
        this.adminStatistiquesService = adminStatistiquesService;
    }

    @GetMapping
    public AdminStatistiquesResponse getStatistiques() {
        return adminStatistiquesService.getStatistiques();
    }
}