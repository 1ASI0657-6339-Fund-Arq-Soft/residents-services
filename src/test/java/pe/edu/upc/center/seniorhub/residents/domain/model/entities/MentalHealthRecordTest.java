package pe.edu.upc.center.seniorhub.residents.domain.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MentalHealthRecord Entity Tests")
class MentalHealthRecordTest {

    private MentalHealthRecord mentalHealthRecord;
    private Date testDate;

    @BeforeEach
    void setUp() {
        testDate = new Date();
        mentalHealthRecord = new MentalHealthRecord(testDate, "Ansiedad generalizada", "Terapia cognitivo-conductual");
    }

    @Test
    @DisplayName("Should create mental health record with valid parameters")
    void shouldCreateMentalHealthRecordWithValidParameters() {
        assertNotNull(mentalHealthRecord);
        assertEquals(testDate, mentalHealthRecord.getRecordDate());
        assertEquals("Ansiedad generalizada", mentalHealthRecord.getDiagnosis());
        assertEquals("Terapia cognitivo-conductual", mentalHealthRecord.getTreatment());
    }

    @Test
    @DisplayName("Should have audit fields")
    void shouldHaveAuditFields() {
        // Los campos de auditoría son manejados por la clase base AuditableAbstractAggregateRoot
        assertNotNull(mentalHealthRecord.getCreatedAt());
        assertNotNull(mentalHealthRecord.getUpdatedAt());
    }

    @Test
    @DisplayName("Should validate diagnosis is not null")
    void shouldValidateDiagnosisIsNotNull() {
        assertNotNull(mentalHealthRecord.getDiagnosis());
        assertFalse(mentalHealthRecord.getDiagnosis().trim().isEmpty());
    }

    @Test
    @DisplayName("Should validate treatment is not null")
    void shouldValidateTreatmentIsNotNull() {
        assertNotNull(mentalHealthRecord.getTreatment());
        assertFalse(mentalHealthRecord.getTreatment().trim().isEmpty());
    }

    @Test
    @DisplayName("Should validate record date is not null")
    void shouldValidateRecordDateIsNotNull() {
        assertNotNull(mentalHealthRecord.getRecordDate());
    }

    @Test
    @DisplayName("Should create mental health record with different parameters")
    void shouldCreateMentalHealthRecordWithDifferentParameters() {
        Date anotherDate = new Date(System.currentTimeMillis() - 86400000); // 1 día atrás
        MentalHealthRecord anotherRecord = new MentalHealthRecord(
                anotherDate, 
                "Depresión leve", 
                "Medicación antidepresiva y sesiones de psicoterapia"
        );

        assertNotNull(anotherRecord);
        assertEquals(anotherDate, anotherRecord.getRecordDate());
        assertEquals("Depresión leve", anotherRecord.getDiagnosis());
        assertEquals("Medicación antidepresiva y sesiones de psicoterapia", anotherRecord.getTreatment());
    }

    @Test
    @DisplayName("Should handle complex mental health diagnoses")
    void shouldHandleComplexMentalHealthDiagnoses() {
        String complexDiagnosis = "Trastorno mixto ansioso-depresivo con síntomas somáticos";
        String complexTreatment = "Tratamiento farmacológico combinado con terapia cognitivo-conductual y técnicas de relajación";
        
        MentalHealthRecord complexRecord = new MentalHealthRecord(testDate, complexDiagnosis, complexTreatment);
        
        assertEquals(complexDiagnosis, complexRecord.getDiagnosis());
        assertEquals(complexTreatment, complexRecord.getTreatment());
    }

    @Test
    @DisplayName("Should handle different types of mental health conditions")
    void shouldHandleDifferentTypesOfMentalHealthConditions() {
        MentalHealthRecord[] records = {
                new MentalHealthRecord(testDate, "Ansiedad social", "Terapia de exposición gradual"),
                new MentalHealthRecord(testDate, "Trastorno bipolar", "Estabilizadores del ánimo"),
                new MentalHealthRecord(testDate, "Trastorno de estrés postraumático", "EMDR y terapia narrativa"),
                new MentalHealthRecord(testDate, "Trastorno obsesivo-compulsivo", "Terapia de prevención de respuesta")
        };

        for (MentalHealthRecord record : records) {
            assertNotNull(record.getDiagnosis());
            assertNotNull(record.getTreatment());
            assertNotNull(record.getRecordDate());
            assertFalse(record.getDiagnosis().trim().isEmpty());
            assertFalse(record.getTreatment().trim().isEmpty());
        }
    }

    @Test
    @DisplayName("Should handle long-term treatment plans")
    void shouldHandleLongTermTreatmentPlans() {
        String longTermTreatment = "Plan de tratamiento a largo plazo que incluye: " +
                "1) Medicación diaria, " +
                "2) Sesiones de terapia semanales, " +
                "3) Técnicas de mindfulness, " +
                "4) Ejercicio regular, " +
                "5) Seguimiento mensual con psiquiatra";
        
        MentalHealthRecord longTermRecord = new MentalHealthRecord(
                testDate, 
                "Trastorno de ansiedad generalizada crónico", 
                longTermTreatment
        );
        
        assertEquals(longTermTreatment, longTermRecord.getTreatment());
        assertTrue(longTermRecord.getTreatment().length() > 100);
    }
}