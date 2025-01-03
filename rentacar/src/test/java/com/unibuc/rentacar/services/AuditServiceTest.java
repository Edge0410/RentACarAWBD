package com.unibuc.rentacar.services;

import com.unibuc.rentacar.entities.Audit;
import com.unibuc.rentacar.repositories.AuditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuditServiceTest {

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private AuditService auditService;

    private Audit mockAudit1;
    private Audit mockAudit2;

    public AuditServiceTest() {
        MockitoAnnotations.openMocks(this);

        mockAudit1 = new Audit("Created entity in service", LocalDateTime.now());
        mockAudit1.setId(1);

        mockAudit2 = new Audit("Created another entity in service", LocalDateTime.now());
        mockAudit2.setId(2);
    }

    @Test
    void getAllAudits_Returns() {
        List<Audit> audits = Arrays.asList(mockAudit1, mockAudit2);
        when(auditRepository.findAll()).thenReturn(audits);

        List<Audit> result = auditService.getAllAudits();

        assertEquals(2, result.size());
        assertEquals("Created entity in service", result.get(0).getEvent());
        verify(auditRepository, times(1)).findAll();
    }

    @Test
    void logAudit_Returns() {
        Audit newAudit = new Audit("System rebooted", LocalDateTime.now());
        newAudit.setId(3);

        when(auditRepository.save(any(Audit.class))).thenReturn(newAudit);

        Audit result = auditService.logAudit("System rebooted");

        assertNotNull(result);
        assertEquals("System rebooted", result.getEvent());
        verify(auditRepository, times(1)).save(any(Audit.class));
    }
}
