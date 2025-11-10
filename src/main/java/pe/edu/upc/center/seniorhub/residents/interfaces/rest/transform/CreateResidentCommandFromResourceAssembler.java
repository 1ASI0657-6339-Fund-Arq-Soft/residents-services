package pe.edu.upc.center.seniorhub.residents.interfaces.rest.transform;

import pe.edu.upc.center.seniorhub.residents.domain.model.commands.CreateResidentCommand;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.Address;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.FullName;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.ReceiptId;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.CreateResidentResource;

public class CreateResidentCommandFromResourceAssembler {

    public static CreateResidentCommand toCommandFromResource(CreateResidentResource resource) {
        FullName fullName = new FullName(resource.firstName(), resource.lastName());
        Address address = new Address(resource.city(), resource.state(), resource.country(),
                resource.street(), resource.zipCode());
        ReceiptId receiptId = new ReceiptId(resource.receiptId());

        return new CreateResidentCommand(
                resource.dni(),
                fullName,
                address,
                resource.birthDate(),
                resource.gender(),
                receiptId
        );
    }
}
