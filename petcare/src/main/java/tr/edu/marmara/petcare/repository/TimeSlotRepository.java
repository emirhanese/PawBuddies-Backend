package tr.edu.marmara.petcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.edu.marmara.petcare.model.Schedule;
import tr.edu.marmara.petcare.model.TimeSlot;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    Optional<List<TimeSlot>> findAllBySchedule(Schedule schedule);
    Optional<TimeSlot> findBySchedule(Schedule schedule);
}
