package tr.edu.marmara.petcare.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import tr.edu.marmara.petcare.model.UserRole;

@Getter
@Setter
public class UserSaveRequest {
    @NotBlank(message = "name cannot be null!")
    private String name;
    @NotBlank(message = "surname cannot be null!")
    private String surname;
    @Email(message = "Email is not well formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email is mandatory")
    private String email;
    @NotEmpty(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;
    @NotEmpty(message = "User role is mandatory")
    @NotNull(message = "User role is mandatory")
    private String role;
}
