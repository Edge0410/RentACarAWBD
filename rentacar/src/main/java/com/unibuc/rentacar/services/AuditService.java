package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Audit;
import com.unibuc.rentacar.repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService{

    @Autowired
    private AuditRepository auditRepository;

    public List<Audit> getAllAudits() {
        return auditRepository.findAll();
    }

    public Audit logAudit(String event) {
        Audit audit = new Audit(event, LocalDateTime.now());
        return auditRepository.save(audit);
    }
}
