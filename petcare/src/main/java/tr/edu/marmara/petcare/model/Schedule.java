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
@Table(name = "veterinary_schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // working days, working hours and available hours must be space separated.
    private String workingDays;
    private String workingHours;
    private String availableHours; // this includes hours which are not reserved.
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "veterinary_id", nullable = false)
    private User veterinary;
}
