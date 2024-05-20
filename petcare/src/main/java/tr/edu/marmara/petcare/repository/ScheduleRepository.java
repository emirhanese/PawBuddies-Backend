package tr.edu.marmara.petcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.edu.marmara.petcare.model.Schedule;
import tr.edu.marmara.petcare.model.User;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<List<Schedule>> findAllByVeterinary(User veterinary);
    Optional<Schedule> findScheduleByVeterinaryAndDayOfWeek(User veterinary, DayOfWeek dayOfWeek);
    Optional<Schedule> findScheduleByVeterinary(User veterinary);
}
