package tr.edu.marmara.petcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.marmara.petcare.dto.PetResponse;
import tr.edu.marmara.petcare.dto.SavePetRequest;
import tr.edu.marmara.petcare.dto.UpdatePetRequest;
import tr.edu.marmara.petcare.service.PetService;


import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pets")
public class PetController {

    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<PetResponse>> getPets() {
        return new ResponseEntity<>(petService.getPets(), HttpStatus.OK);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetResponse> getPetById(@PathVariable UUID petId) {
        return new ResponseEntity<>(petService.getPetById(petId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PetResponse> savePet(@RequestParam UUID userId, @RequestBody SavePetRequest petSaveRequest) {
        return new ResponseEntity<>(petService.savePet(userId, petSaveRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{petId}")
    public ResponseEntity<PetResponse> updatePet(@PathVariable UUID petId,
                                                 @RequestBody UpdatePetRequest petUpdateRequest) {
        return new ResponseEntity<>(petService.updatePet(petId, petUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<PetResponse> deletePet(@PathVariable UUID petId) {
        return new ResponseEntity<>(petService.deletePet(petId), HttpStatus.OK);
    }
}
