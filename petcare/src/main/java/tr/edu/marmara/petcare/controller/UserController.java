package tr.edu.marmara.petcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.marmara.petcare.dto.PetResponse;
import tr.edu.marmara.petcare.dto.UserResponse;
import tr.edu.marmara.petcare.dto.UserSaveRequest;
import tr.edu.marmara.petcare.dto.UserUpdateRequest;
import tr.edu.marmara.petcare.service.PetService;
import tr.edu.marmara.petcare.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserSaveRequest userSaveRequest) {
        return new ResponseEntity<>(userService.saveUser(userSaveRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID userId,
                                                   @RequestBody UserUpdateRequest userUpdateRequest) {
        return new ResponseEntity<>(userService.updateUser(userId, userUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable UUID userId) {
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }
    @GetMapping("/getPets/{userId}")
    public ResponseEntity<List<PetResponse>> getPets(@PathVariable UUID userId) {
        return new ResponseEntity<>(userService.getPetsByUserId(userId), HttpStatus.OK);
    }
}
