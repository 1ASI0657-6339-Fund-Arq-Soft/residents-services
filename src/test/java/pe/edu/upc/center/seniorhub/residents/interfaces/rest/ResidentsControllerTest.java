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
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetAllResidentsQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetResidentByIdQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetResidentByDniQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.FullName;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.Address;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.ReceiptId;
import pe.edu.upc.center.seniorhub.residents.domain.services.ResidentCommandService;
import pe.edu.upc.center.seniorhub.residents.domain.services.ResidentQueryService;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.CreateResidentResource;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.transform.CreateResidentCommandFromResourceAssembler;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.transform.ResidentResourceFromEntityAssembler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResidentsController.class)
class ResidentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResidentCommandService residentCommandService;

    @MockBean
    private ResidentQueryService residentQueryService;

    @MockBean
    private CreateResidentCommandFromResourceAssembler createAssembler;

    @MockBean
    private ResidentResourceFromEntityAssembler resourceAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    private Resident resident;
    private CreateResidentResource createResource;

    @BeforeEach
    void setUp() {
        FullName fullName = new FullName("Juan", "Pérez");
        Address address = new Address("Lima", "Lima", "Perú", "Av. Brasil 123", "15001");
        ReceiptId receiptId = new ReceiptId(1001L);
        
        CreateResidentCommand command = new CreateResidentCommand(
                "12345678", fullName, address, new Date(), "Masculino", receiptId
        );

        resident = new Resident(command);

        createResource = new CreateResidentResource(
                "12345678", "Juan", "Pérez", "Lima", "Lima", "Perú", 
                "Av. Brasil 123", "15001", new Date(), "Masculino", 1001L
        );
    }

    @Test
    void testCreateResident() throws Exception {
        // Given
        when(createAssembler.toCommandFromResource(any(CreateResidentResource.class)))
                .thenReturn(any(CreateResidentCommand.class));
        when(residentCommandService.handle(any(CreateResidentCommand.class))).thenReturn(1L);

        // When & Then
        mockMvc.perform(post("/api/v1/residents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createResource)))
                .andExpect(status().isCreated());

        verify(residentCommandService, times(1)).handle(any(CreateResidentCommand.class));
    }

    @Test
    void testGetAllResidents() throws Exception {
        // Given
        List<Resident> residents = Arrays.asList(resident);
        when(residentQueryService.handle(any(GetAllResidentsQuery.class))).thenReturn(residents);

        // When & Then
        mockMvc.perform(get("/api/v1/residents"))
                .andExpect(status().isOk());

        verify(residentQueryService, times(1)).handle(any(GetAllResidentsQuery.class));
    }

    @Test
    void testGetResidentById() throws Exception {
        // Given
        when(residentQueryService.handle(any(GetResidentByIdQuery.class)))
                .thenReturn(Optional.of(resident));

        // When & Then
        mockMvc.perform(get("/api/v1/residents/1"))
                .andExpect(status().isOk());

        verify(residentQueryService, times(1)).handle(any(GetResidentByIdQuery.class));
    }

    @Test
    void testGetResidentByIdNotFound() throws Exception {
        // Given
        when(residentQueryService.handle(any(GetResidentByIdQuery.class)))
                .thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/v1/residents/1"))
                .andExpect(status().isNotFound());

        verify(residentQueryService, times(1)).handle(any(GetResidentByIdQuery.class));
    }

    @Test
    void testGetResidentByDni() throws Exception {
        // Given
        when(residentQueryService.handle(any(GetResidentByDniQuery.class)))
                .thenReturn(Optional.of(resident));

        // When & Then
        mockMvc.perform(get("/api/v1/residents/dni/12345678"))
                .andExpect(status().isOk());

        verify(residentQueryService, times(1)).handle(any(GetResidentByDniQuery.class));
    }

    @Test
    void testGetResidentByDniNotFound() throws Exception {
        // Given
        when(residentQueryService.handle(any(GetResidentByDniQuery.class)))
                .thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/v1/residents/dni/99999999"))
                .andExpect(status().isNotFound());

        verify(residentQueryService, times(1)).handle(any(GetResidentByDniQuery.class));
    }

    @Test
    void testCreateResidentWithInvalidData() throws Exception {
        // Given
        CreateResidentResource invalidResource = new CreateResidentResource(
                "", "", "", "", "", "", "", "", null, "", 0L
        );

        // When & Then
        mockMvc.perform(post("/api/v1/residents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidResource)))
                .andExpect(status().isBadRequest());
    }
}