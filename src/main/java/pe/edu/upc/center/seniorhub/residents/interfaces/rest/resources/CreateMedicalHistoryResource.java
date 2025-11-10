package pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources;

import java.util.Date;

public record CreateMedicalHistoryResource(
        String diagnosis,
        String description,
        Date dateOfDiagnosis,
        String doctor
) {}