package tr.edu.marmara.petcare.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "time_slots")
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ElementCollection
    @MapKeyColumn(name="hours")
    @Column(name="isAvailable")
    @CollectionTable(name="available_hours", joinColumns=@JoinColumn(name="available_hours_id"))
    private Map<String, Boolean> availableHours = new HashMap<>();
}
