package pe.edu.upc.center.seniorhub.residents.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.seniorhub.residents.domain.model.aggregates.Resident;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

    boolean existsByDni(String dni);

    boolean existsByDniAndIdIsNot(String dni, Long id);

    Optional<Resident> findByDni(String dni);

    List<Resident> findByGender(String gender);
}
