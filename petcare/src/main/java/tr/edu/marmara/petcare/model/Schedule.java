package tr.edu.marmara.petcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

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
    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL)
    private TimeSlot timeSlot;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "veterinary_id", nullable = false)
    private User veterinary;
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
        this.timeSlot.setSchedule(this);
    }
}
