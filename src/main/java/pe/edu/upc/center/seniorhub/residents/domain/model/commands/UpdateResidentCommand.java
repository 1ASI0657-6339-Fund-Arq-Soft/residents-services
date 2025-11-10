package pe.edu.upc.center.seniorhub.residents.domain.model.commands;

import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.Address;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.FullName;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.ReceiptId;

import java.util.Date;

public record UpdateResidentCommand(
        Long residentId,
        String dni,
        FullName fullName,
        Address address,
        Date birthDate,
        String gender,
        ReceiptId receipt
) {}
