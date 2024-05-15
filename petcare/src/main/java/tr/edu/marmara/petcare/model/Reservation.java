package tr.edu.marmara.petcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reservations")
public class Reservation extends BaseModel {
    private String subject;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_id")
    private Pet pet;
    private String reservationDay;
    private String reservationTime;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User reservationOwner;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "veterinary_id")
    private Veterinary veterinary;
}
