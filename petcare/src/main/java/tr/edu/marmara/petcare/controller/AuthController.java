package tr.edu.marmara.petcare.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.marmara.petcare.dto.*;
import tr.edu.marmara.petcare.service.AuthService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserSaveRequest userSaveRequest) throws MessagingException {
        authService.register(userSaveRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody @Valid AuthRequest authenticationRequest,
            HttpServletResponse response
    ) throws IOException {
        return new ResponseEntity<>(authService.authenticate(authenticationRequest, response), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }

    @GetMapping("/activate-account")
    public void confirm(@RequestParam String token) throws MessagingException {
        authService.activateAccount(token);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody UserUpdateRequest userUpdateRequest, @RequestParam String token) throws MessagingException {
        authService.resetPassword(userUpdateRequest, token);
    }

    @PostMapping("/forgot-password")
    public void sendResetPasswordEmail(@RequestBody String email) throws MessagingException {
        authService.sendPasswordResetEmail(email);
    }
}
