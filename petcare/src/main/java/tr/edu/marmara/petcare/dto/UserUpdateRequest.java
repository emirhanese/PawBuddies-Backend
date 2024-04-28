package tr.edu.marmara.petcare.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
}
