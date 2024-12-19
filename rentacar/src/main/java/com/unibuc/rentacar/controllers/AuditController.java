package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.Audit;
import com.unibuc.rentacar.services.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping
    public List<Audit> getAllAudits() {
        return auditService.getAllAudits();
    }

    @PostMapping
    public Audit logEvent(@RequestBody String event) {
        return auditService.createAudit(event);
    }
}
