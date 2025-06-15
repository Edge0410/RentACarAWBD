package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.Audit;
import com.unibuc.rentacar.services.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Audit Visualisation Controller")
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @Operation(summary = "View Audit Log", description = "Retrieve all audit events")
    @GetMapping
    public List<Audit> getAllAudits() {
        return auditService.getAllAudits();
    }

    @Operation(summary = "Log Event", description = "Add event into the Audit log")
    @PostMapping
    public Audit logEvent(@RequestBody String event) {
        return auditService.logAudit(event);
    }
}
