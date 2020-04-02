package mg.tabera.gisio.services.map;

import mg.tabera.gisio.model.Owner;
import mg.tabera.gisio.model.Pet;
import mg.tabera.gisio.services.OwnerService;
import mg.tabera.gisio.services.PetService;
import mg.tabera.gisio.services.PetTypeService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Owner findByLastName(String lastName) {
        return super.findAll().stream()
                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
                .findFirst().orElse(null);
    }

    @Override
    public List<Owner> findAllByLastNameLike(String lastName) {
        return this.findAll()
                .stream()
                .filter(owners -> owners.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner owner) {
        super.delete(owner);
    }

    @Override
    public Owner save(Owner owner) {

        if(owner != null){
            if(owner.getPets() != null){
                owner.getPets().forEach( pet ->{
                    if(pet.getPetType() != null){
                        if(pet.getPetType().getId() == null){
                            pet.setPetType(petTypeService.save(pet.getPetType()));
                        }
                    }else{
                        throw new RuntimeException("Pet Type is required");
                    }

                    if(pet.getId() == null){
                        Pet savedPet = petService.save(pet);
                        pet.setId(savedPet.getId());
                    }

                });
            }
            return super.save(owner);
        }

        return null;
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }
}
