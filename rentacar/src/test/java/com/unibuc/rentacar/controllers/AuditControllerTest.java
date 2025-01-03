package com.unibuc.rentacar.controllers;

import com.unibuc.rentacar.entities.Audit;
import com.unibuc.rentacar.services.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

class AuditControllerTest {

    @Mock
    private AuditService auditService;

    @InjectMocks
    private AuditController auditController;

    private MockMvc mockMvc;
    private Audit mockAudit1;
    private Audit mockAudit2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(auditController).build();

        mockAudit1 = new Audit("User logged in", LocalDateTime.now());
        mockAudit1.setId(1);

        mockAudit2 = new Audit("Car rented", LocalDateTime.now());
        mockAudit2.setId(2);
    }

    @Test
    void getAllAudits_Returns() throws Exception {
        List<Audit> audits = Arrays.asList(mockAudit1, mockAudit2);
        when(auditService.getAllAudits()).thenReturn(audits);

        mockMvc.perform(get("/api/audit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].event", is("User logged in")))
                .andExpect(jsonPath("$[1].event", is("Car rented")));

        verify(auditService, times(1)).getAllAudits();
    }

}
