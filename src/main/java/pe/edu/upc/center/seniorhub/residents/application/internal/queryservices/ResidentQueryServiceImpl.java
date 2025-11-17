package pe.edu.upc.center.seniorhub.residents.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.seniorhub.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetAllResidentsQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetResidentByDniQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetResidentByIdQuery;
import pe.edu.upc.center.seniorhub.residents.domain.services.ResidentQueryService;
import pe.edu.upc.center.seniorhub.residents.infrastructure.persistence.jpa.repositories.ResidentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ResidentQueryServiceImpl implements ResidentQueryService {

    private final ResidentRepository residentRepository;

    public ResidentQueryServiceImpl(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public List<Resident> handle(GetAllResidentsQuery query) {
        return residentRepository.findAll();
    }

    @Override
    public Optional<Resident> handle(GetResidentByIdQuery query) {
        return residentRepository.findById(query.residentId());
    }

    @Override
    public Optional<Resident> handle(GetResidentByDniQuery query) {
        return residentRepository.findByDni(query.dni());
    }
}
