package pe.edu.upc.center.seniorhub.residents.interfaces.rest.transform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.seniorhub.residents.domain.model.commands.CreateResidentCommand;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.CreateResidentResource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CreateResidentCommandFromResourceAssemblerTest {

    private CreateResidentResource resource;
    private Date birthDate;

    @BeforeEach
    void setUp() {
        birthDate = new Date();
        resource = new CreateResidentResource(
                "12345678",
                "Juan",
                "Pérez",
                "Lima",
                "Lima",
                "Perú",
                "Av. Brasil 123",
                "15001",
                birthDate,
                "Masculino",
                1001L
        );
    }

    @Test
    void testToCommandFromResource() {
        // When
        CreateResidentCommand command = CreateResidentCommandFromResourceAssembler
                .toCommandFromResource(resource);

        // Then
        assertNotNull(command);
        assertEquals("12345678", command.dni());
        assertEquals("Juan", command.fullName().firstName());
        assertEquals("Pérez", command.fullName().lastName());
        assertEquals("Lima", command.address().city());
        assertEquals("Lima", command.address().state());
        assertEquals("Perú", command.address().country());
        assertEquals("Av. Brasil 123", command.address().street());
        assertEquals("15001", command.address().zipCode());
        assertEquals(birthDate, command.birthDate());
        assertEquals("Masculino", command.gender());
        assertEquals(1001L, command.receipt().receiptId());
    }

    @Test
    void testToCommandFromResourceWithDifferentData() {
        // Given
        Date differentBirthDate = new Date(System.currentTimeMillis() - 86400000); // 1 day ago
        CreateResidentResource differentResource = new CreateResidentResource(
                "87654321",
                "Ana",
                "García",
                "Cusco",
                "Cusco",
                "Perú",
                "Av. Sol 456",
                "08001",
                differentBirthDate,
                "Femenino",
                2002L
        );

        // When
        CreateResidentCommand command = CreateResidentCommandFromResourceAssembler
                .toCommandFromResource(differentResource);

        // Then
        assertNotNull(command);
        assertEquals("87654321", command.dni());
        assertEquals("Ana", command.fullName().firstName());
        assertEquals("García", command.fullName().lastName());
        assertEquals("Cusco", command.address().city());
        assertEquals("Femenino", command.gender());
        assertEquals(2002L, command.receipt().receiptId());
    }

    @Test
    void testToCommandFromResourceMaintainsValueObjectIntegrity() {
        // When
        CreateResidentCommand command = CreateResidentCommandFromResourceAssembler
                .toCommandFromResource(resource);

        // Then
        assertNotNull(command.fullName());
        assertNotNull(command.address());
        assertNotNull(command.receipt());
        
        // Verify FullName integrity
        assertNotNull(command.fullName().firstName());
        assertNotNull(command.fullName().lastName());
        assertFalse(command.fullName().firstName().isBlank());
        assertFalse(command.fullName().lastName().isBlank());
        
        // Verify Address integrity
        assertNotNull(command.address().city());
        assertNotNull(command.address().state());
        assertNotNull(command.address().country());
        assertNotNull(command.address().street());
        assertNotNull(command.address().zipCode());
        
        // Verify ReceiptId integrity
        assertNotNull(command.receipt().receiptId());
        assertTrue(command.receipt().receiptId() > 0);
    }

    @Test
    void testToCommandFromResourceWithMinimalValidData() {
        // Given
        CreateResidentResource minimalResource = new CreateResidentResource(
                "11111111",
                "A",
                "B",
                "C",
                "D",
                "E",
                "F",
                "G",
                new Date(),
                "M",
                1L
        );

        // When
        CreateResidentCommand command = CreateResidentCommandFromResourceAssembler
                .toCommandFromResource(minimalResource);

        // Then
        assertNotNull(command);
        assertEquals("11111111", command.dni());
        assertEquals("A", command.fullName().firstName());
        assertEquals("B", command.fullName().lastName());
        assertEquals("C", command.address().city());
        assertEquals("D", command.address().state());
        assertEquals("E", command.address().country());
        assertEquals("F", command.address().street());
        assertEquals("G", command.address().zipCode());
        assertEquals("M", command.gender());
        assertEquals(1L, command.receipt().receiptId());
    }

    @Test
    void testResourceToCommandMapping() {
        // When
        CreateResidentCommand command = CreateResidentCommandFromResourceAssembler
                .toCommandFromResource(resource);

        // Then - Verify all fields are correctly mapped
        assertEquals(resource.dni(), command.dni());
        assertEquals(resource.firstName(), command.fullName().firstName());
        assertEquals(resource.lastName(), command.fullName().lastName());
        assertEquals(resource.city(), command.address().city());
        assertEquals(resource.state(), command.address().state());
        assertEquals(resource.country(), command.address().country());
        assertEquals(resource.street(), command.address().street());
        assertEquals(resource.zipCode(), command.address().zipCode());
        assertEquals(resource.birthDate(), command.birthDate());
        assertEquals(resource.gender(), command.gender());
        assertEquals(resource.receiptId(), command.receipt().receiptId());
    }
}