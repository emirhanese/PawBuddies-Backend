package tr.edu.marmara.petcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.edu.marmara.petcare.model.Pet;

import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {

}
