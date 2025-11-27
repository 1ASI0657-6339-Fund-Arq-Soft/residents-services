package pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources;

public record ResidentDetailsResource(
        Long id,
        String fullName,
        String dni
) {}
