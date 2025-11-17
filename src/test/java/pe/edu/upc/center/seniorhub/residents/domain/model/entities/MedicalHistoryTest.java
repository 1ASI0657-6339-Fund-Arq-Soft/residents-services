package pe.edu.upc.center.seniorhub.residents.domain.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MedicalHistory Entity Tests")
class MedicalHistoryTest {

    private MedicalHistory medicalHistory;
    private Date testDate;

    @BeforeEach
    void setUp() {
        testDate = new Date();
        medicalHistory = new MedicalHistory(testDate, "Hipertension arterial", "Medicacion antihipertensiva");
    }

    @Test
    @DisplayName("Should create medical history with valid parameters")
    void shouldCreateMedicalHistoryWithValidParameters() {
        assertNotNull(medicalHistory);
        assertEquals(testDate, medicalHistory.getRecordDate());
        assertEquals("Hipertension arterial", medicalHistory.getDiagnosis());
        assertEquals("Medicacion antihipertensiva", medicalHistory.getTreatment());
    }

    @Test
    @DisplayName("Should have audit fields")
    void shouldHaveAuditFields() {
        // Los campos de auditoría son manejados por la clase base AuditableAbstractAggregateRoot
        assertNotNull(medicalHistory.getCreatedAt());
        assertNotNull(medicalHistory.getUpdatedAt());
    }

    @Test
    @DisplayName("Should validate diagnosis is not null")
    void shouldValidateDiagnosisIsNotNull() {
        assertNotNull(medicalHistory.getDiagnosis());
        assertFalse(medicalHistory.getDiagnosis().trim().isEmpty());
    }

    @Test
    @DisplayName("Should validate treatment is not null")
    void shouldValidateTreatmentIsNotNull() {
        assertNotNull(medicalHistory.getTreatment());
        assertFalse(medicalHistory.getTreatment().trim().isEmpty());
    }

    @Test
    @DisplayName("Should validate record date is not null")
    void shouldValidateRecordDateIsNotNull() {
        assertNotNull(medicalHistory.getRecordDate());
    }

    @Test
    @DisplayName("Should create medical history with different parameters")
    void shouldCreateMedicalHistoryWithDifferentParameters() {
        Date anotherDate = new Date(System.currentTimeMillis() - 86400000); // 1 día atrás
        MedicalHistory anotherHistory = new MedicalHistory(
                anotherDate, 
                "Diabetes tipo 2", 
                "Control dietético y medicación"
        );

        assertNotNull(anotherHistory);
        assertEquals(anotherDate, anotherHistory.getRecordDate());
        assertEquals("Diabetes tipo 2", anotherHistory.getDiagnosis());
        assertEquals("Control dietético y medicación", anotherHistory.getTreatment());
    }

    @Test
    @DisplayName("Should handle long diagnosis and treatment texts")
    void shouldHandleLongDiagnosisAndTreatmentTexts() {
        String longDiagnosis = "Diagnóstico complejo que incluye múltiples condiciones médicas y requiere evaluación especializada";
        String longTreatment = "Tratamiento integral que incluye medicación, terapia física, cambios dietéticos y seguimiento médico regular";
        
        MedicalHistory complexHistory = new MedicalHistory(testDate, longDiagnosis, longTreatment);
        
        assertEquals(longDiagnosis, complexHistory.getDiagnosis());
        assertEquals(longTreatment, complexHistory.getTreatment());
    }
}