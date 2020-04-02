package mg.tabera.gisio.repositories;

import mg.tabera.gisio.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
