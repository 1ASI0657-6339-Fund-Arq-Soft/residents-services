package pe.edu.upc.center.seniorhub.residents.interfaces.rest.transform;

import pe.edu.upc.center.seniorhub.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.ResidentResource;

<<<<<<< HEAD
import java.util.List;
import java.util.stream.Collectors;

=======
>>>>>>> develop
public class ResidentResourceFromEntityAssembler {
    public static ResidentResource toResourceFromEntity(Resident resident) {
        var fullName = resident.getFullName();
        var address = resident.getAddress();
        var receipt = resident.getReceipt();

        return new ResidentResource(
                resident.getId(),
                resident.getDni(),
                fullName != null ? fullName.firstName() : null,
                fullName != null ? fullName.lastName() : null,
                address != null ? address.city() : null,
                address != null ? address.state() : null,
                address != null ? address.country() : null,
                address != null ? address.street() : null,
                address != null ? address.zipCode() : null,
                resident.getBirthDate(),
                resident.getGender(),
                receipt != null ? receipt.receiptId() : null
        );
    }
<<<<<<< HEAD

    public static List<ResidentResource> toResourcesFromEntities(List<Resident> residents) {
        return residents.stream()
                .map(ResidentResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }
=======
>>>>>>> develop
}
