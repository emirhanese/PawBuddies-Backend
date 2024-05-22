package tr.edu.marmara.petcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

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
    private Long id;
    // working days, working hours and available hours must be space separated.
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @ElementCollection
    @MapKeyColumn(name="hours")
    @Column(name="isAvailable")
    @CollectionTable(name="available_hours", joinColumns=@JoinColumn(name="schedule_id"))
    private Map<String, Boolean> availableHours = new HashMap<>();
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "veterinary_id", nullable = false)
    private User veterinary;
}
