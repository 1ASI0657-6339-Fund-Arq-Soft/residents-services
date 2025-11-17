package pe.edu.upc.center.seniorhub.residents.domain.services;

import pe.edu.upc.center.seniorhub.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetAllResidentsQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetResidentByDniQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetResidentByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ResidentQueryService {
    List<Resident> handle(GetAllResidentsQuery query);
    Optional<Resident> handle(GetResidentByIdQuery query);
    Optional<Resident> handle(GetResidentByDniQuery query);
}
