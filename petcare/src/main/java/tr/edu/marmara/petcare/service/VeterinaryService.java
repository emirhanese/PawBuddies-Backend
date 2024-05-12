package tr.edu.marmara.petcare.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tr.edu.marmara.petcare.dto.VeterinaryResponse;
import tr.edu.marmara.petcare.dto.VeterinaryUpdateRequest;
import tr.edu.marmara.petcare.model.UserRole;
import tr.edu.marmara.petcare.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VeterinaryService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public VeterinaryService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    public List<VeterinaryResponse> getVeterinaries() {
        return userRepository.findAllByRole(String.valueOf(UserRole.VETERINARY)).stream()
                .map((user -> modelMapper.map(user, VeterinaryResponse.class)))
                .collect(Collectors.toList());
    }

    public VeterinaryResponse getVeterinaryById(UUID veterinaryId) {
    }

    public VeterinaryResponse updateVeterinary(UUID veterinaryId, VeterinaryUpdateRequest veterinaryUpdateRequest) {
    }

    public VeterinaryResponse deleteVeterinary(UUID veterinaryId) {
    }
}
