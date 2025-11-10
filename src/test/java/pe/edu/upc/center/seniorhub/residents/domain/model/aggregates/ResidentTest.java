package pe.edu.upc.center.seniorhub.residents.domain.model.aggregates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import pe.edu.upc.center.seniorhub.residents.domain.model.commands.CreateResidentCommand;
import pe.edu.upc.center.seniorhub.residents.domain.model.entities.MedicalHistory;
import pe.edu.upc.center.seniorhub.residents.domain.model.entities.Medication;
import pe.edu.upc.center.seniorhub.residents.domain.model.entities.MentalHealthRecord;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.FullName;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.Address;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.ReceiptId;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Resident Aggregate Tests")
class ResidentTest {

    private Resident resident;
    private CreateResidentCommand command;

    @BeforeEach
    void setUp() {
        FullName fullName = new FullName("Juan", "Perez");
        Address address = new Address("Lima", "Lima", "Peru", "Av. Lima 123", "15001");
        ReceiptId receiptId = new ReceiptId(1L);
        
        command = new CreateResidentCommand(
                "12345678",
                fullName,
                address,
                new Date(),
                "M",
                receiptId
        );
        resident = new Resident(command);
    }

    @Test
    @DisplayName("Should create resident with valid command")
    void shouldCreateResidentWithValidCommand() {
        assertNotNull(resident);
        assertEquals("12345678", resident.getDni());
        assertEquals("Juan", resident.getFullName().firstName());
        assertEquals("Perez", resident.getFullName().lastName());
        assertEquals("Lima", resident.getAddress().city());
        assertEquals("M", resident.getGender());
        assertEquals(1L, resident.getReceipt().receiptId());
    }

    @Test
    @DisplayName("Should get full name as string")
    void shouldGetFullNameAsString() {
        String fullName = resident.getFullNameAsString();
        assertEquals("Juan Perez", fullName);
    }

    @Test
    @DisplayName("Should add medication successfully")
    void shouldAddMedicationSuccessfully() {
        Medication medication = new Medication();
        int initialSize = resident.getMedication().size();
        
        resident.addMedication(medication);
        
        assertEquals(initialSize + 1, resident.getMedication().size());
        assertTrue(resident.getMedication().contains(medication));
    }

    @Test
    @DisplayName("Should remove medication successfully")
    void shouldRemoveMedicationSuccessfully() {
        Medication medication = new Medication();
        resident.addMedication(medication);
        int initialSize = resident.getMedication().size();
        
        resident.removeMedication(medication);
        
        assertEquals(initialSize - 1, resident.getMedication().size());
        assertFalse(resident.getMedication().contains(medication));
    }

    @Test
    @DisplayName("Should add medical history successfully")
    void shouldAddMedicalHistorySuccessfully() {
        MedicalHistory history = new MedicalHistory();
        int initialSize = resident.getMedicalHistories().size();
        
        resident.addMedicalHistory(history);
        
        assertEquals(initialSize + 1, resident.getMedicalHistories().size());
        assertTrue(resident.getMedicalHistories().contains(history));
    }

    @Test
    @DisplayName("Should add mental health record successfully")
    void shouldAddMentalHealthRecordSuccessfully() {
        MentalHealthRecord record = new MentalHealthRecord();
        int initialSize = resident.getMentalHealthRecords().size();
        
        resident.addMentalHealthRecord(record);
        
        assertEquals(initialSize + 1, resident.getMentalHealthRecords().size());
        assertTrue(resident.getMentalHealthRecords().contains(record));
    }

    @Test
    @DisplayName("Should update resident information")
    void shouldUpdateResidentInformation() {
        FullName newFullName = new FullName("Carlos", "Lopez");
        Address newAddress = new Address("Arequipa", "Arequipa", "Peru", "Av. Ejercito 456", "04001");
        ReceiptId newReceiptId = new ReceiptId(2L);
        Date newBirthDate = new Date();
        
        resident.updateInformation("87654321", newFullName, newAddress, newBirthDate, "F", newReceiptId);
        
        assertEquals("87654321", resident.getDni());
        assertEquals("Carlos", resident.getFullName().firstName());
        assertEquals("Lopez", resident.getFullName().lastName());
        assertEquals("Arequipa", resident.getAddress().city());
        assertEquals("F", resident.getGender());
        assertEquals(2L, resident.getReceipt().receiptId());
    }

    @Test
    @DisplayName("Should initialize with empty collections")
    void shouldInitializeWithEmptyCollections() {
        assertNotNull(resident.getMedication());
        assertNotNull(resident.getMedicalHistories());
        assertNotNull(resident.getMentalHealthRecords());
        assertTrue(resident.getMedication().isEmpty());
        assertTrue(resident.getMedicalHistories().isEmpty());
        assertTrue(resident.getMentalHealthRecords().isEmpty());
    }

    @Test
    @DisplayName("Should validate DNI is not null or empty")
    void shouldValidateDniIsNotNullOrEmpty() {
        assertNotNull(resident.getDni());
        assertFalse(resident.getDni().trim().isEmpty());
    }

    @Test
    @DisplayName("Should validate person name is not null")
    void shouldValidatePersonNameIsNotNull() {
        assertNotNull(resident.getFullName());
        assertNotNull(resident.getFullName().firstName());
        assertNotNull(resident.getFullName().lastName());
    }
}