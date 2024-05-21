package tr.edu.marmara.petcare.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.edu.marmara.petcare.dto.VeterinaryResponse;
import tr.edu.marmara.petcare.dto.VeterinaryUpdateRequest;
import tr.edu.marmara.petcare.model.UserRole;
import tr.edu.marmara.petcare.repository.AddressRepository;
import tr.edu.marmara.petcare.repository.DocumentRepository;
import tr.edu.marmara.petcare.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VeterinaryService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    private final DocumentRepository documentRepository;

    public VeterinaryService(UserRepository userRepository, ModelMapper modelMapper, AddressRepository addressRepository, DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
        this.documentRepository = documentRepository;
    }

    public List<VeterinaryResponse> getVeterinaries() {
        return userRepository.findAllByRole(UserRole.VETERINARY).orElseThrow().stream()
                .map((user -> new VeterinaryResponse(
                        user.getId(),
                        user.getCreatedAt(),
                        user.getUpdatedAt(),
                        user.getName(),
                        user.getSurname(),
                        user.getEmail(),
                        user.getRole(),
                        addressRepository.findAddressByVeterinary(user),
                        documentRepository.findDocumentByVeterinary(user)
                )))
                .collect(Collectors.toList());
    }

    public VeterinaryResponse getVeterinaryById(UUID veterinaryId) {
        var user = userRepository.findById(veterinaryId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with given ID!"));
        return new VeterinaryResponse(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getRole(),
                addressRepository.findAddressByVeterinary(user),
                documentRepository.findDocumentByVeterinary(user)
        );
    }

    public VeterinaryResponse updateVeterinary(UUID veterinaryId, VeterinaryUpdateRequest veterinaryUpdateRequest) {
        var user = userRepository.findById(veterinaryId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with given ID!"));
        modelMapper.map(veterinaryUpdateRequest, user);
        userRepository.save(user);
        return modelMapper.map(user, VeterinaryResponse.class);
    }

    public VeterinaryResponse deleteVeterinary(UUID veterinaryId) {
        var user = userRepository.findById(veterinaryId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with given ID!"));
        userRepository.deleteById(veterinaryId);
        return modelMapper.map(user, VeterinaryResponse.class);
    }
}
