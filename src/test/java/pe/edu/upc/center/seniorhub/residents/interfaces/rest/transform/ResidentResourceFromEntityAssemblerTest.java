package pe.edu.upc.center.seniorhub.residents.interfaces.rest.transform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.seniorhub.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.seniorhub.residents.domain.model.commands.CreateResidentCommand;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.FullName;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.Address;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.ReceiptId;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.ResidentResource;

import java.util.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResidentResourceFromEntityAssemblerTest {

    private Resident resident;
    private Date birthDate;

    @BeforeEach
    void setUp() {
        birthDate = new Date();
        FullName fullName = new FullName("Juan", "Pérez");
        Address address = new Address("Lima", "Lima", "Perú", "Av. Brasil 123", "15001");
        ReceiptId receiptId = new ReceiptId(1001L);
        
        CreateResidentCommand command = new CreateResidentCommand(
                "12345678", fullName, address, birthDate, "Masculino", receiptId
        );

        resident = new Resident(command);
    }

    @Test
    void testToResourceFromEntity() {
        // When
        ResidentResource resource = ResidentResourceFromEntityAssembler
                .toResourceFromEntity(resident);

        // Then
        assertNotNull(resource);
        assertEquals(resident.getDni(), resource.dni());
        assertEquals(resident.getFullName().firstName(), resource.firstName());
        assertEquals(resident.getFullName().lastName(), resource.lastName());
        assertEquals(resident.getAddress().city(), resource.city());
        assertEquals(resident.getAddress().state(), resource.state());
        assertEquals(resident.getAddress().country(), resource.country());
        assertEquals(resident.getAddress().street(), resource.street());
        assertEquals(resident.getAddress().zipCode(), resource.zipCode());
        assertEquals(resident.getBirthDate(), resource.birthDate());
        assertEquals(resident.getGender(), resource.gender());
        assertEquals(resident.getReceipt().receiptId(), resource.receiptId());
    }

    @Test
    void testToResourcesFromEntities() {
        // Given
        FullName fullName2 = new FullName("Ana", "García");
        Address address2 = new Address("Cusco", "Cusco", "Perú", "Av. Sol 456", "08001");
        ReceiptId receiptId2 = new ReceiptId(2002L);
        
        CreateResidentCommand command2 = new CreateResidentCommand(
                "87654321", fullName2, address2, new Date(), "Femenino", receiptId2
        );

        Resident anotherResident = new Resident(command2);
        List<Resident> residents = Arrays.asList(resident, anotherResident);

        // When
        List<ResidentResource> resources = ResidentResourceFromEntityAssembler
                .toResourcesFromEntities(residents);

        // Then
        assertNotNull(resources);
        assertEquals(2, resources.size());
        
        ResidentResource resource1 = resources.get(0);
        assertEquals("12345678", resource1.dni());
        assertEquals("Juan", resource1.firstName());
        assertEquals("Pérez", resource1.lastName());
        
        ResidentResource resource2 = resources.get(1);
        assertEquals("87654321", resource2.dni());
        assertEquals("Ana", resource2.firstName());
        assertEquals("García", resource2.lastName());
    }

    @Test
    void testToResourcesFromEmptyList() {
        // Given
        List<Resident> emptyList = Arrays.asList();

        // When
        List<ResidentResource> resources = ResidentResourceFromEntityAssembler
                .toResourcesFromEntities(emptyList);

        // Then
        assertNotNull(resources);
        assertTrue(resources.isEmpty());
    }

    @Test
    void testEntityToResourceMapping() {
        // When
        ResidentResource resource = ResidentResourceFromEntityAssembler
                .toResourceFromEntity(resident);

        // Then - Verify all entity fields are correctly mapped to resource
        assertEquals(resident.getDni(), resource.dni());
        assertEquals(resident.getFullName().firstName(), resource.firstName());
        assertEquals(resident.getFullName().lastName(), resource.lastName());
        assertEquals(resident.getAddress().city(), resource.city());
        assertEquals(resident.getAddress().state(), resource.state());
        assertEquals(resident.getAddress().country(), resource.country());
        assertEquals(resident.getAddress().street(), resource.street());
        assertEquals(resident.getAddress().zipCode(), resource.zipCode());
        assertEquals(resident.getBirthDate(), resource.birthDate());
        assertEquals(resident.getGender(), resource.gender());
        assertEquals(resident.getReceipt().receiptId(), resource.receiptId());
    }

    @Test
    void testResourceMaintainsDataIntegrity() {
        // When
        ResidentResource resource = ResidentResourceFromEntityAssembler
                .toResourceFromEntity(resident);

        // Then
        assertNotNull(resource.dni());
        assertNotNull(resource.firstName());
        assertNotNull(resource.lastName());
        assertNotNull(resource.city());
        assertNotNull(resource.state());
        assertNotNull(resource.country());
        assertNotNull(resource.street());
        assertNotNull(resource.zipCode());
        assertNotNull(resource.birthDate());
        assertNotNull(resource.gender());
        assertNotNull(resource.receiptId());
        
        assertFalse(resource.dni().isBlank());
        assertFalse(resource.firstName().isBlank());
        assertFalse(resource.lastName().isBlank());
        assertTrue(resource.receiptId() > 0);
    }

    @Test
    void testSpecialCharactersHandling() {
        // Given
        FullName specialName = new FullName("José", "Rodríguez");
        Address specialAddress = new Address("São Paulo", "São Paulo", "Brasil", "Rua José María", "01000-000");
        ReceiptId receiptId = new ReceiptId(3003L);
        
        CreateResidentCommand command = new CreateResidentCommand(
                "11111111", specialName, specialAddress, new Date(), "Masculino", receiptId
        );

        Resident specialResident = new Resident(command);

        // When
        ResidentResource resource = ResidentResourceFromEntityAssembler
                .toResourceFromEntity(specialResident);

        // Then
        assertEquals("José", resource.firstName());
        assertEquals("Rodríguez", resource.lastName());
        assertEquals("São Paulo", resource.city());
        assertEquals("São Paulo", resource.state());
        assertEquals("Brasil", resource.country());
        assertEquals("Rua José María", resource.street());
        assertEquals("01000-000", resource.zipCode());
    }

    @Test
    void testNullHandling() {
        // Given
        FullName fullName = new FullName("Test", "User");
        Address address = new Address("Test", "Test", "Test", "Test", "Test");
        ReceiptId receiptId = new ReceiptId(1L);
        
        CreateResidentCommand command = new CreateResidentCommand(
                "00000000", fullName, address, new Date(), "Test", receiptId
        );

        Resident testResident = new Resident(command);

        // When
        ResidentResource resource = ResidentResourceFromEntityAssembler
                .toResourceFromEntity(testResident);

        // Then
        assertNotNull(resource);
        // All fields should be non-null since value objects enforce this
        assertNotNull(resource.dni());
        assertNotNull(resource.firstName());
        assertNotNull(resource.lastName());
        assertNotNull(resource.receiptId());
    }
}