package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Audit;

import java.util.List;

public interface IAuditService {
    List<Audit> getAllAudits();
    Audit createAudit(String event);
}
