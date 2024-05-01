package tr.edu.marmara.petcare.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.edu.marmara.petcare.dto.*;
import tr.edu.marmara.petcare.exception.ActivationTokenException;
import tr.edu.marmara.petcare.exception.UserAlreadyExistAuthenticationException;
import tr.edu.marmara.petcare.model.*;
import tr.edu.marmara.petcare.repository.JwtTokenRepository;
import tr.edu.marmara.petcare.repository.TokenRepository;
import tr.edu.marmara.petcare.repository.UserRepository;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final AddressService addressService;
    private final JwtTokenRepository jwtTokenRepository;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public MessageResponse register(UserSaveRequest registerRequest) throws MessagingException {
        var user = userRepository.findByEmail(registerRequest.getEmail());
        if(user.isPresent()) {
            throw new UserAlreadyExistAuthenticationException("User already exists with given email!");
        }
        var userToBeSaved = new User();
        userToBeSaved = User.builder()
                .name(registerRequest.getName())
                .surname(registerRequest.getSurname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(UserRole.valueOf(registerRequest.getRole()))
                .isEnabled(false)
                .isExpired(false)
                .isLocked(false)
                .userState(UserState.PENDING)
                .build();

        userRepository.save(userToBeSaved);

        if(registerRequest.getRole().equals("VETERINARY")) {
            addressService.saveAddress(userToBeSaved, registerRequest.getAddress());
        }

        sendValidationEmail(userToBeSaved);

        return new MessageResponse("User saved successfully.");
    }

    public AuthResponse authenticate(AuthRequest authenticationRequest,
                                     HttpServletResponse response) throws IOException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.email(),
                        authenticationRequest.password()
                )
        );

        var user = userRepository.findByEmail(authenticationRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("user not found with given email"));
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getId());
        claims.put("user_role", user.getRole());
        var jwtToken = jwtService.generateToken(claims, user);
        var refreshToken = jwtService.generateRefreshToken(claims, user);
        revokeAllTokenByUser(user);
        saveUserToken(jwtToken, user);
        saveUserToken(refreshToken, user);
        return new AuthResponse(jwtToken, refreshToken);
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String email = jwtService.extractUsername(refreshTokenRequest.token());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with given email"));

        if(jwtService.isTokenValid(refreshTokenRequest.token(), user)) {
            var jwtToken = jwtService.generateToken(user);
            saveUserToken(jwtToken, user);
            return new AuthResponse(jwtToken, refreshTokenRequest.token());
        }

        return null;
    }

    //@Transactional
    public MessageResponse activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new ActivationTokenException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new ActivationTokenException("Activation token has expired. A new token has been send to the same email address");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(user.getRole().equals(UserRole.valueOf("USER"))) {
            user.setEnabled(true);
            user.setUserState(UserState.APPROVED);
        }
        else {
            user.setUserState(UserState.APPROVED);
        }
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

        return new MessageResponse("Account activated successfully.");
    }

    public MessageResponse resetPassword(PasswordResetRequest passwordResetRequest) throws MessagingException {
        var user = userRepository.findByEmail(passwordResetRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(passwordResetRequest.password()));
        userRepository.save(user);

        return new MessageResponse("Password reset successfully.");
    }

    public MessageResponse isPasswordResetTokenValid(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new ActivationTokenException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new ActivationTokenException("Token has expired. A new token has been send to the same email address");
        }

        userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new MessageResponse("Password Reset token is correct.");
    }

    public MessageResponse sendPasswordResetEmail(String email) throws MessagingException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with given email!"));

        var newToken = generateAndSavePasswordResetToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.RESET_PASSWORD,
                activationUrl,
                newToken,
                "Reset Password"
        );

        return new MessageResponse("Password reset email sent successfully.");
    }
    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateVerificationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private String generateAndSavePasswordResetToken(User user) {
        // Generate a token
        String generatedToken = generateVerificationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(2))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateVerificationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

    private void revokeAllTokenByUser(User user) {
        List<JwtToken> validTokens = jwtTokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        jwtTokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String jwt, User user) {
        JwtToken token = new JwtToken();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        jwtTokenRepository.save(token);
    }
}
