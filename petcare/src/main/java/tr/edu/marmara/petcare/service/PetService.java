package tr.edu.marmara.petcare.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tr.edu.marmara.petcare.dto.PetResponse;
import tr.edu.marmara.petcare.dto.SavePetRequest;
import tr.edu.marmara.petcare.dto.UpdatePetRequest;
import tr.edu.marmara.petcare.model.Pet;
import tr.edu.marmara.petcare.model.User;
import tr.edu.marmara.petcare.repository.PetRepository;
import tr.edu.marmara.petcare.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public List<PetResponse> getPets() {
        return petRepository.findAll()
                .stream()
                .map(pet -> modelMapper.map(pet, PetResponse.class))
                .collect(Collectors.toList());
    }

    public PetResponse getPetById(UUID petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow();
        return modelMapper.map(pet, PetResponse.class);
    }

    public PetResponse savePet(UUID userId, SavePetRequest pet) {
        Pet savedPet = Pet.builder()
                .name(pet.name())
                .type(pet.type())
                .gender(pet.gender())
                .image(pet.image())
                .specialInfo(pet.specialInfo())
                .weight(pet.weight())
                .height(pet.height())
                .birthDate(pet.birthDate())
                .build();
        User owner = userRepository.findById(userId)
                        .orElseThrow();
        savedPet.setOwner(owner);
        return modelMapper.map(petRepository.save(savedPet), PetResponse.class);
    }

    public PetResponse updatePet(UUID petId, UpdatePetRequest petUpdateRequest) {
        Pet petToBeUpdated = petRepository.findById(petId)
                .orElseThrow();

        modelMapper.map(petUpdateRequest, petToBeUpdated);
        petRepository.save(petToBeUpdated);

        return modelMapper.map(petToBeUpdated, PetResponse.class);
    }

    public PetResponse deletePet(UUID petId) {
        PetResponse petToBeDeleted = getPetById(petId);
        petRepository.deleteById(petId);
        return petToBeDeleted;
    }
}
