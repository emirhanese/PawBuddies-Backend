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
    public ResponseEntity<MessageResponse> register(@RequestBody @Valid UserSaveRequest userSaveRequest) throws MessagingException {
        return new ResponseEntity<>(authService.register(userSaveRequest), HttpStatus.ACCEPTED);
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
    public ResponseEntity<MessageResponse> confirm(@RequestParam String token) throws MessagingException {
        return new ResponseEntity<>(authService.activateAccount(token), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws MessagingException {
        return new ResponseEntity<>(authService.resetPassword(passwordResetRequest), HttpStatus.OK);
    }

    @GetMapping("/isPasswordResetTokenValid")
    public ResponseEntity<MessageResponse> isPasswordResetTokenValid(@RequestParam String token) throws MessagingException {
        return new ResponseEntity<>(authService.isPasswordResetTokenValid(token), HttpStatus.OK);
    }

    @PostMapping("/sendResetPasswordEmail")
    public ResponseEntity<MessageResponse> sendResetPasswordEmail(@RequestParam String email) throws MessagingException {
        return new ResponseEntity<>(authService.sendPasswordResetEmail(email), HttpStatus.OK);
    }
}
