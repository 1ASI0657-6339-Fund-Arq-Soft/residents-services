package pe.edu.upc.center.seniorhub.residents.interfaces.rest.transform;

import pe.edu.upc.center.seniorhub.residents.domain.model.commands.UpdateResidentCommand;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.Address;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.FullName;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.ReceiptId;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.ResidentResource;

public class UpdateResidentCommandFromResourceAssembler {

    public static UpdateResidentCommand toCommandFromResource(Long residentId, ResidentResource resource) {
        FullName fullName = new FullName(resource.firstName(), resource.lastName());
        Address address = new Address(resource.city(), resource.state(), resource.country(),
                resource.street(), resource.zipCode());
        ReceiptId receiptId = new ReceiptId(resource.receiptId());

        return new UpdateResidentCommand(
                residentId,
                resource.dni(),
                fullName,
                address,
                resource.birthDate(),
                resource.gender(),
                receiptId
        );
    }
}
