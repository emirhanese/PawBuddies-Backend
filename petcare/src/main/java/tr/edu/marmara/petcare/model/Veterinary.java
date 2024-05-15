package tr.edu.marmara.petcare.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
public class Veterinary extends User {
    @OneToMany(mappedBy = "veterinary")
    private List<Address> address;
    @OneToOne(mappedBy = "veterinary")
    private Document document;
    @OneToMany(mappedBy = "veterinary")
    private List<Reservation> reservations;
    @OneToOne(mappedBy = "veterinary")
    private Schedule schedule;
}
