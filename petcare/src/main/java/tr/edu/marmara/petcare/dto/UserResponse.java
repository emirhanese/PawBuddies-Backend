package tr.edu.marmara.petcare.dto;

import lombok.Getter;
import lombok.Setter;
import tr.edu.marmara.petcare.model.BaseModel;
import tr.edu.marmara.petcare.model.UserRole;

@Getter
@Setter
public class UserResponse extends BaseModel {
    private String name;
    private String surname;
    private String email;
    private UserRole role;
}
