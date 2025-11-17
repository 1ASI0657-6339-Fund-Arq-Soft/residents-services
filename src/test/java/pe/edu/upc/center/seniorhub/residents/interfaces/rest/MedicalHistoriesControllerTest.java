package pe.edu.upc.center.seniorhub.residents.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pe.edu.upc.center.seniorhub.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.seniorhub.residents.domain.model.commands.CreateResidentCommand;
import pe.edu.upc.center.seniorhub.residents.domain.model.entities.MedicalHistory;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetResidentByIdQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.FullName;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.Address;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.ReceiptId;
import pe.edu.upc.center.seniorhub.residents.domain.services.ResidentQueryService;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.CreateMedicalHistoryResource;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicalHistoriesController.class)
class MedicalHistoriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResidentQueryService residentQueryService;

    @Autowired
    private ObjectMapper objectMapper;

    private Resident resident;
    private CreateMedicalHistoryResource createResource;

    @BeforeEach
    void setUp() {
        FullName fullName = new FullName("Juan", "Pérez");
        Address address = new Address("Lima", "Lima", "Perú", "Av. Brasil 123", "15001");
        ReceiptId receiptId = new ReceiptId(1001L);
        
        CreateResidentCommand command = new CreateResidentCommand(
                "12345678", fullName, address, new Date(), "Masculino", receiptId
        );

        resident = new Resident(command);

        createResource = new CreateMedicalHistoryResource(
                "Diabetes Tipo 2",
                "Condición crónica diagnosticada en 2020",
                new Date(),
                "Dr. García"
        );
    }

    @Test
    void testCreateMedicalHistory() throws Exception {
        // Given
        when(residentQueryService.handle(any(GetResidentByIdQuery.class)))
                .thenReturn(Optional.of(resident));

        // When & Then
        mockMvc.perform(post("/api/v1/residents/1/medical-histories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createResource)))
                .andExpect(status().isCreated());

        verify(residentQueryService, times(1)).handle(any(GetResidentByIdQuery.class));
    }

    @Test
    void testCreateMedicalHistoryResidentNotFound() throws Exception {
        // Given
        when(residentQueryService.handle(any(GetResidentByIdQuery.class)))
                .thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(post("/api/v1/residents/99/medical-histories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createResource)))
                .andExpect(status().isNotFound());

        verify(residentQueryService, times(1)).handle(any(GetResidentByIdQuery.class));
    }

    @Test
    void testGetMedicalHistoriesByResidentId() throws Exception {
        // Given
        when(residentQueryService.handle(any(GetResidentByIdQuery.class)))
                .thenReturn(Optional.of(resident));

        // When & Then
        mockMvc.perform(get("/api/v1/residents/1/medical-histories"))
                .andExpect(status().isOk());

        verify(residentQueryService, times(1)).handle(any(GetResidentByIdQuery.class));
    }

    @Test
    void testGetMedicalHistoriesResidentNotFound() throws Exception {
        // Given
        when(residentQueryService.handle(any(GetResidentByIdQuery.class)))
                .thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/v1/residents/99/medical-histories"))
                .andExpect(status().isNotFound());

        verify(residentQueryService, times(1)).handle(any(GetResidentByIdQuery.class));
    }

    @Test
    void testCreateMedicalHistoryWithInvalidData() throws Exception {
        // Given
        CreateMedicalHistoryResource invalidResource = new CreateMedicalHistoryResource(
                "", "", null, ""
        );

        when(residentQueryService.handle(any(GetResidentByIdQuery.class)))
                .thenReturn(Optional.of(resident));

        // When & Then
        mockMvc.perform(post("/api/v1/residents/1/medical-histories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidResource)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetMedicalHistoryById() throws Exception {
        // Given
        when(residentQueryService.handle(any(GetResidentByIdQuery.class)))
                .thenReturn(Optional.of(resident));

        // When & Then
        mockMvc.perform(get("/api/v1/residents/1/medical-histories/1"))
                .andExpect(status().isOk());

        verify(residentQueryService, times(1)).handle(any(GetResidentByIdQuery.class));
    }

    @Test
    void testUpdateMedicalHistory() throws Exception {
        // Given
        when(residentQueryService.handle(any(GetResidentByIdQuery.class)))
                .thenReturn(Optional.of(resident));

        // When & Then
        mockMvc.perform(put("/api/v1/residents/1/medical-histories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createResource)))
                .andExpect(status().isOk());

        verify(residentQueryService, times(1)).handle(any(GetResidentByIdQuery.class));
    }

    @Test
    void testDeleteMedicalHistory() throws Exception {
        // Given
        when(residentQueryService.handle(any(GetResidentByIdQuery.class)))
                .thenReturn(Optional.of(resident));

        // When & Then
        mockMvc.perform(delete("/api/v1/residents/1/medical-histories/1"))
                .andExpect(status().isNoContent());

        verify(residentQueryService, times(1)).handle(any(GetResidentByIdQuery.class));
    }
}