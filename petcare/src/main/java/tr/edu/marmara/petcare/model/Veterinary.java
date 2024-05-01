package tr.edu.marmara.petcare.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
public class Veterinary extends User {
    @OneToMany(mappedBy = "veterinary")
    private List<Address> address;
}
