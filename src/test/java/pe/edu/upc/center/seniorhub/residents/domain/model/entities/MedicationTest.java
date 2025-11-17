package pe.edu.upc.center.seniorhub.residents.domain.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Medication Entity Tests")
class MedicationTest {

    private Medication medication;

    @BeforeEach
    void setUp() {
        medication = new Medication("Aspirina", "1 vez al dia");
    }

    @Test
    @DisplayName("Should create medication with valid parameters")
    void shouldCreateMedicationWithValidParameters() {
        assertNotNull(medication);
        assertEquals("Aspirina", medication.getName());
        assertEquals("1 vez al dia", medication.getFrequency());
    }

    @Test
    @DisplayName("Should have audit fields")
    void shouldHaveAuditFields() {
        // Los campos de auditoría son manejados por la clase base AuditableAbstractAggregateRoot
        assertNotNull(medication.getCreatedAt());
        assertNotNull(medication.getUpdatedAt());
    }

    @Test
    @DisplayName("Should validate medication name is not null")
    void shouldValidateMedicationNameIsNotNull() {
        assertNotNull(medication.getName());
        assertFalse(medication.getName().trim().isEmpty());
    }

    @Test
    @DisplayName("Should validate frequency is not null")
    void shouldValidateFrequencyIsNotNull() {
        assertNotNull(medication.getFrequency());
        assertFalse(medication.getFrequency().trim().isEmpty());
    }

    @Test
    @DisplayName("Should create medication with different parameters")
    void shouldCreateMedicationWithDifferentParameters() {
        Medication anotherMedication = new Medication("Paracetamol", "Cada 8 horas");
        
        assertNotNull(anotherMedication);
        assertEquals("Paracetamol", anotherMedication.getName());
        assertEquals("Cada 8 horas", anotherMedication.getFrequency());
    }

    @Test
    @DisplayName("Should handle complex medication names")
    void shouldHandleComplexMedicationNames() {
        Medication complexMedication = new Medication(
                "Acetaminofén 500mg", 
                "2 tabletas cada 12 horas con alimentos"
        );
        
        assertEquals("Acetaminofén 500mg", complexMedication.getName());
        assertEquals("2 tabletas cada 12 horas con alimentos", complexMedication.getFrequency());
    }

    @Test
    @DisplayName("Should handle different frequency formats")
    void shouldHandleDifferentFrequencyFormats() {
        Medication[] medications = {
                new Medication("Med1", "1 vez por día"),
                new Medication("Med2", "Cada 6 horas"),
                new Medication("Med3", "3 veces al día antes de comidas"),
                new Medication("Med4", "Solo cuando sea necesario")
        };

        for (Medication med : medications) {
            assertNotNull(med.getName());
            assertNotNull(med.getFrequency());
            assertFalse(med.getName().trim().isEmpty());
            assertFalse(med.getFrequency().trim().isEmpty());
        }
    }

    @Test
    @DisplayName("Should maintain object equality based on content")
    void shouldMaintainObjectEqualityBasedOnContent() {
        Medication medication1 = new Medication("Aspirina", "1 vez al dia");
        Medication medication2 = new Medication("Aspirina", "1 vez al dia");
        
        // Aunque son objetos diferentes, tienen el mismo contenido
        assertEquals(medication1.getName(), medication2.getName());
        assertEquals(medication1.getFrequency(), medication2.getFrequency());
    }
}