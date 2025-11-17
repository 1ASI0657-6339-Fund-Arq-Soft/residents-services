package pe.edu.upc.center.seniorhub.residents.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.seniorhub.residents.domain.model.commands.CreateResidentCommand;
import pe.edu.upc.center.seniorhub.residents.domain.model.commands.UpdateResidentCommand;
import pe.edu.upc.center.seniorhub.residents.domain.model.commands.DeleteResidentCommand;
import pe.edu.upc.center.seniorhub.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.FullName;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.Address;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.ReceiptId;
import pe.edu.upc.center.seniorhub.residents.infrastructure.persistence.jpa.repositories.ResidentRepository;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResidentCommandServiceImplTest {

    @Mock
    private ResidentRepository residentRepository;

    @InjectMocks
    private ResidentCommandServiceImpl residentCommandService;

    private CreateResidentCommand createCommand;
    private UpdateResidentCommand updateCommand;
    private DeleteResidentCommand deleteCommand;
    private Resident resident;

    @BeforeEach
    void setUp() {
        FullName fullName = new FullName("Juan", "Pérez");
        Address address = new Address("Lima", "Lima", "Perú", "Av. Brasil 123", "15001");
        ReceiptId receiptId = new ReceiptId(1001L);
        
        createCommand = new CreateResidentCommand(
                "12345678", fullName, address, new Date(), "Masculino", receiptId
        );

        FullName updateFullName = new FullName("Ana", "García");
        Address updateAddress = new Address("Cusco", "Cusco", "Perú", "Av. Sol 456", "08001");
        ReceiptId updateReceiptId = new ReceiptId(2002L);
        
        updateCommand = new UpdateResidentCommand(
                1L, "87654321", updateFullName, updateAddress, new Date(), "Femenino", updateReceiptId
        );

        deleteCommand = new DeleteResidentCommand(1L);

        resident = new Resident(createCommand);
    }

    @Test
    void testCreateResident() {
        // Given
        when(residentRepository.save(any(Resident.class))).thenReturn(resident);

        // When
        Long result = residentCommandService.handle(createCommand);

        // Then
        assertNotNull(result);
        verify(residentRepository, times(1)).save(any(Resident.class));
    }

    @Test
    void testUpdateResident() {
        // Given
        when(residentRepository.findById(1L)).thenReturn(Optional.of(resident));
        when(residentRepository.save(any(Resident.class))).thenReturn(resident);

        // When
        Optional<Resident> result = residentCommandService.handle(updateCommand);

        // Then
        assertTrue(result.isPresent());
        verify(residentRepository, times(1)).findById(1L);
        verify(residentRepository, times(1)).save(any(Resident.class));
    }

    @Test
    void testUpdateResidentNotFound() {
        // Given
        when(residentRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<Resident> result = residentCommandService.handle(updateCommand);

        // Then
        assertFalse(result.isPresent());
        verify(residentRepository, times(1)).findById(1L);
        verify(residentRepository, never()).save(any(Resident.class));
    }

    @Test
    void testDeleteResident() {
        // Given
        when(residentRepository.findById(1L)).thenReturn(Optional.of(resident));

        // When
        residentCommandService.handle(deleteCommand);

        // Then
        verify(residentRepository, times(1)).findById(1L);
        verify(residentRepository, times(1)).delete(resident);
    }

    @Test
    void testDeleteResidentNotFound() {
        // Given
        when(residentRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        residentCommandService.handle(deleteCommand);

        // Then
        verify(residentRepository, times(1)).findById(1L);
        verify(residentRepository, never()).delete(any(Resident.class));
    }

    @Test
    void testCreateResidentWithInvalidData() {
        // Given
        when(residentRepository.save(any(Resident.class))).thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> residentCommandService.handle(createCommand));
        verify(residentRepository, times(1)).save(any(Resident.class));
    }
}