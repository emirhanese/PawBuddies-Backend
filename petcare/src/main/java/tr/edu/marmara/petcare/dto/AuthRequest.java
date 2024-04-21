package tr.edu.marmara.petcare.dto;

import jakarta.validation.constraints.*;

public record AuthRequest(
        @Email(message = "invalid email address!")
        @NotEmpty(message = "Email is mandatory")
        @NotNull(message = "Email is mandatory")
        String email,
        @NotEmpty(message = "Password is mandatory")
        @NotNull(message = "Password is mandatory")
        @Size(min = 8, message = "Password should be 8 characters long minimum")
        String password) {
}
