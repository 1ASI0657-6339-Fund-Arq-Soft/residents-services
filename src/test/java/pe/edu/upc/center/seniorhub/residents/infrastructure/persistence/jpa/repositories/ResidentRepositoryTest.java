package pe.edu.upc.center.seniorhub.residents.infrastructure.persistence.jpa.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pe.edu.upc.center.seniorhub.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.seniorhub.residents.domain.model.commands.CreateResidentCommand;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.FullName;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.Address;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.ReceiptId;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ResidentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ResidentRepository residentRepository;

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
    void testSaveResident() {
        // When
        Resident savedResident = residentRepository.save(resident1);

        // Then
        assertNotNull(savedResident);
        assertNotNull(savedResident.getId());
        assertEquals("12345678", savedResident.getDni());
        assertEquals("Juan", savedResident.getFullName().firstName());
        assertEquals("Pérez", savedResident.getFullName().lastName());
    }

    @Test
    void testFindById() {
        // Given
        Resident savedResident = entityManager.persistAndFlush(resident1);
        Long residentId = savedResident.getId();

        // When
        Optional<Resident> foundResident = residentRepository.findById(residentId);

        // Then
        assertTrue(foundResident.isPresent());
        assertEquals("12345678", foundResident.get().getDni());
        assertEquals("Juan", foundResident.get().getFullName().firstName());
        assertEquals("Pérez", foundResident.get().getFullName().lastName());
    }

    @Test
    void testFindByDni() {
        // Given
        entityManager.persistAndFlush(resident1);

        // When
        Optional<Resident> foundResident = residentRepository.findByDni("12345678");

        // Then
        assertTrue(foundResident.isPresent());
        assertEquals("12345678", foundResident.get().getDni());
        assertEquals("Juan", foundResident.get().getFullName().firstName());
    }

    @Test
    void testFindByDniNotFound() {
        // When
        Optional<Resident> foundResident = residentRepository.findByDni("99999999");

        // Then
        assertFalse(foundResident.isPresent());
    }

    @Test
    void testFindAll() {
        // Given
        entityManager.persistAndFlush(resident1);
        entityManager.persistAndFlush(resident2);

        // When
        List<Resident> residents = residentRepository.findAll();

        // Then
        assertNotNull(residents);
        assertEquals(2, residents.size());
        
        // Verify that both residents are in the list
        boolean foundResident1 = residents.stream()
                .anyMatch(r -> "12345678".equals(r.getDni()));
        boolean foundResident2 = residents.stream()
                .anyMatch(r -> "87654321".equals(r.getDni()));
        
        assertTrue(foundResident1);
        assertTrue(foundResident2);
    }

    @Test
    void testDeleteResident() {
        // Given
        Resident savedResident = entityManager.persistAndFlush(resident1);
        Long residentId = savedResident.getId();

        // When
        residentRepository.deleteById(residentId);

        // Then
        Optional<Resident> deletedResident = residentRepository.findById(residentId);
        assertFalse(deletedResident.isPresent());
    }

    @Test
    void testExistsByDni() {
        // Given
        entityManager.persistAndFlush(resident1);

        // When
        boolean exists = residentRepository.existsByDni("12345678");
        boolean notExists = residentRepository.existsByDni("99999999");

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void testUpdateResident() {
        // Given
        Resident savedResident = entityManager.persistAndFlush(resident1);
        Long residentId = savedResident.getId();

        // When
        FullName newFullName = new FullName("Juan Carlos", "Pérez López");
        Address newAddress = new Address("Arequipa", "Arequipa", "Perú", "Av. Ejercito 789", "04001");
        ReceiptId newReceiptId = new ReceiptId(3003L);
        
        savedResident.updateInformation("12345678", newFullName, newAddress, 
                new Date(), "Masculino", newReceiptId);
        
        Resident updatedResident = residentRepository.save(savedResident);

        // Then
        assertNotNull(updatedResident);
        assertEquals(residentId, updatedResident.getId());
        assertEquals("Juan Carlos", updatedResident.getFullName().firstName());
        assertEquals("Pérez López", updatedResident.getFullName().lastName());
        assertEquals("Arequipa", updatedResident.getAddress().city());
        assertEquals(3003L, updatedResident.getReceipt().receiptId());
    }

    @Test
    void testUniqueConstraintOnDni() {
        // Given
        entityManager.persistAndFlush(resident1);

        // Create another resident with same DNI
        FullName duplicateFullName = new FullName("Other", "Person");
        Address duplicateAddress = new Address("Other", "Other", "Other", "Other", "Other");
        ReceiptId duplicateReceiptId = new ReceiptId(9999L);
        
        CreateResidentCommand duplicateCommand = new CreateResidentCommand(
                "12345678", duplicateFullName, duplicateAddress, new Date(), "Other", duplicateReceiptId
        );
        
        Resident duplicateResident = new Resident(duplicateCommand);

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(duplicateResident);
        });
    }

    @Test
    void testValueObjectsPersistence() {
        // Given
        Resident savedResident = entityManager.persistAndFlush(resident1);
        Long residentId = savedResident.getId();

        // When
        Optional<Resident> foundResident = residentRepository.findById(residentId);

        // Then
        assertTrue(foundResident.isPresent());
        Resident resident = foundResident.get();
        
        // Verify FullName value object
        assertNotNull(resident.getFullName());
        assertEquals("Juan", resident.getFullName().firstName());
        assertEquals("Pérez", resident.getFullName().lastName());
        
        // Verify Address value object
        assertNotNull(resident.getAddress());
        assertEquals("Lima", resident.getAddress().city());
        assertEquals("Lima", resident.getAddress().state());
        assertEquals("Perú", resident.getAddress().country());
        assertEquals("Av. Brasil 123", resident.getAddress().street());
        assertEquals("15001", resident.getAddress().zipCode());
        
        // Verify ReceiptId value object
        assertNotNull(resident.getReceipt());
        assertEquals(1001L, resident.getReceipt().receiptId());
    }
}