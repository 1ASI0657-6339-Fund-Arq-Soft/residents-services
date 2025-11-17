package pe.edu.upc.center.seniorhub.residents.application.internal.queryservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.seniorhub.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.seniorhub.residents.domain.model.commands.CreateResidentCommand;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetAllResidentsQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetResidentByIdQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetResidentByDniQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.FullName;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.Address;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.ReceiptId;
import pe.edu.upc.center.seniorhub.residents.infrastructure.persistence.jpa.repositories.ResidentRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResidentQueryServiceImplTest {

    @Mock
    private ResidentRepository residentRepository;

    @InjectMocks
    private ResidentQueryServiceImpl residentQueryService;

    private Resident resident1;
    private Resident resident2;

    @BeforeEach
    void setUp() {
        FullName fullName1 = new FullName("Juan", "Pérez");
        Address address1 = new Address("Lima", "Lima", "Perú", "Av. Brasil 123", "15001");
        ReceiptId receiptId1 = new ReceiptId(1001L);
        
        CreateResidentCommand command1 = new CreateResidentCommand(
                "12345678", fullName1, address1, new Date(), "Masculino", receiptId1
        );

        FullName fullName2 = new FullName("Ana", "García");
        Address address2 = new Address("Cusco", "Cusco", "Perú", "Av. Sol 456", "08001");
        ReceiptId receiptId2 = new ReceiptId(2002L);
        
        CreateResidentCommand command2 = new CreateResidentCommand(
                "87654321", fullName2, address2, new Date(), "Femenino", receiptId2
        );

        resident1 = new Resident(command1);
        resident2 = new Resident(command2);
    }

    @Test
    void testGetAllResidents() {
        // Given
        List<Resident> residents = Arrays.asList(resident1, resident2);
        when(residentRepository.findAll()).thenReturn(residents);

        // When
        List<Resident> result = residentQueryService.handle(new GetAllResidentsQuery());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(resident1));
        assertTrue(result.contains(resident2));
        verify(residentRepository, times(1)).findAll();
    }

    @Test
    void testGetResidentById() {
        // Given
        when(residentRepository.findById(1L)).thenReturn(Optional.of(resident1));

        // When
        Optional<Resident> result = residentQueryService.handle(new GetResidentByIdQuery(1L));

        // Then
        assertTrue(result.isPresent());
        assertEquals(resident1, result.get());
        verify(residentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetResidentByIdNotFound() {
        // Given
        when(residentRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<Resident> result = residentQueryService.handle(new GetResidentByIdQuery(1L));

        // Then
        assertFalse(result.isPresent());
        verify(residentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetResidentByDni() {
        // Given
        when(residentRepository.findByDni("12345678")).thenReturn(Optional.of(resident1));

        // When
        Optional<Resident> result = residentQueryService.handle(new GetResidentByDniQuery("12345678"));

        // Then
        assertTrue(result.isPresent());
        assertEquals(resident1, result.get());
        verify(residentRepository, times(1)).findByDni("12345678");
    }

    @Test
    void testGetResidentByDniNotFound() {
        // Given
        when(residentRepository.findByDni("99999999")).thenReturn(Optional.empty());

        // When
        Optional<Resident> result = residentQueryService.handle(new GetResidentByDniQuery("99999999"));

        // Then
        assertFalse(result.isPresent());
        verify(residentRepository, times(1)).findByDni("99999999");
    }

    @Test
    void testGetAllResidentsEmptyResult() {
        // Given
        when(residentRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Resident> result = residentQueryService.handle(new GetAllResidentsQuery());

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(residentRepository, times(1)).findAll();
    }

    @Test
    void testRepositoryException() {
        // Given
        when(residentRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> residentQueryService.handle(new GetAllResidentsQuery()));
        verify(residentRepository, times(1)).findAll();
    }
}