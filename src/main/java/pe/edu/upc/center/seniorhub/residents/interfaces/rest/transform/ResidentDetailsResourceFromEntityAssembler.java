package pe.edu.upc.center.seniorhub.residents.interfaces.rest.transform;

import pe.edu.upc.center.seniorhub.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.ResidentDetailsResource;

public class ResidentDetailsResourceFromEntityAssembler {

    public static ResidentDetailsResource toResourceFromEntity(Resident resident) {
        return new ResidentDetailsResource(
                resident.getId(),
                resident.getFullNameAsString(),
<<<<<<< HEAD
                resident.getDni(),
                resident.getMedication(),
                resident.getMedicalHistories(),
                resident.getMentalHealthRecords()
=======
                resident.getDni()
>>>>>>> develop
        );
    }
}
