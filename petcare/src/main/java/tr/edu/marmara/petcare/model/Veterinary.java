package tr.edu.marmara.petcare.model;

import jakarta.persistence.*;
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
    @OneToMany(mappedBy = "veterinary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> schedule;
}
