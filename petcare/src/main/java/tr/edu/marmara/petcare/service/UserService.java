package tr.edu.marmara.petcare.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.edu.marmara.petcare.dto.PetResponse;
import tr.edu.marmara.petcare.dto.UserResponse;
import tr.edu.marmara.petcare.dto.UserSaveRequest;
import tr.edu.marmara.petcare.dto.UserUpdateRequest;
import tr.edu.marmara.petcare.model.User;
import tr.edu.marmara.petcare.model.UserRole;
import tr.edu.marmara.petcare.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(); // Custom exception handling is going to be made.
        return modelMapper.map(user, UserResponse.class);
    }

    public UserResponse saveUser(UserSaveRequest userSaveRequest) {
        User savedUser = User.builder()
                .email(userSaveRequest.getEmail())
                .surname(userSaveRequest.getSurname())
                .name(userSaveRequest.getName())
                .password(passwordEncoder.encode(userSaveRequest.getPassword()))
                .role(UserRole.valueOf("USER"))
                .build();

        userRepository.save(savedUser);
        return modelMapper.map(savedUser, UserResponse.class);
    }

    public UserResponse updateUser(UUID userId, UserUpdateRequest userUpdateRequest) {
        User userToBeUpdated = userRepository.findById(userId)
                .orElseThrow();

        modelMapper.map(userUpdateRequest, userToBeUpdated);
        userRepository.save(userToBeUpdated);

        return modelMapper.map(userToBeUpdated, UserResponse.class);
    }

    public UserResponse deleteUser(UUID userId) {
        UserResponse userToBeDeleted = getUserById(userId);
        userRepository.deleteById(userId);
        return userToBeDeleted;
    }

    public List<PetResponse> getPetsByUserId(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with given ID!"));
        return user.getPets().stream()
                .map(pet -> modelMapper.map(pet, PetResponse.class))
                .collect(Collectors.toList());
    }
}
