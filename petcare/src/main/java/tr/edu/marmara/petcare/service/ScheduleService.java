package tr.edu.marmara.petcare.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.edu.marmara.petcare.dto.MessageResponse;
import tr.edu.marmara.petcare.dto.ScheduleSaveRequest;
import tr.edu.marmara.petcare.dto.ScheduleUpdateRequest;
import tr.edu.marmara.petcare.exception.ScheduleNotFoundException;
import tr.edu.marmara.petcare.model.Schedule;
import tr.edu.marmara.petcare.model.TimeSlot;
import tr.edu.marmara.petcare.repository.ScheduleRepository;
import tr.edu.marmara.petcare.repository.TimeSlotRepository;
import tr.edu.marmara.petcare.repository.UserRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, ModelMapper modelMapper,
                           UserRepository userRepository, TimeSlotRepository timeSlotRepository) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public List<Schedule> getSchedulesOfVeterinary(UUID veterinaryId) {
        var veterinary = userRepository.findById(veterinaryId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with given ID!"));
        return scheduleRepository.findAllByVeterinary(veterinary)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with given ID!"));
    }
    public Schedule getScheduleOfVeterinaryForSpecificDay(UUID veterinaryId, String day) {
        var veterinary = userRepository.findById(veterinaryId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with given ID!"));
        return scheduleRepository.findScheduleByVeterinaryAndDayOfWeek(veterinary, DayOfWeek.valueOf(day))
                .orElseThrow();
    }
    public MessageResponse saveSchedule(ScheduleSaveRequest scheduleSaveRequest) {
        var schedule = Schedule.builder()
                .dayOfWeek(scheduleSaveRequest.dayOfWeek())
                //.timeSlot(scheduleSaveRequest.timeSlot())
                .veterinary(scheduleSaveRequest.veterinary())
                .build();
        scheduleRepository.save(schedule);
        return new MessageResponse("Schedule created successfully");
    }

    public void saveAllSchedules(List<ScheduleSaveRequest> scheduleSaveRequests) {
        for (ScheduleSaveRequest scheduleSaveRequest : scheduleSaveRequests) {
            var schedule = new Schedule();
            TimeSlot beginningTimeSlot = new TimeSlot();
            beginningTimeSlot.setAvailableHours(Map.of("09.00 - 11.00", true,
                    "11.00 - 13.00", true,
                    "13.00 - 15.00", true,
                    "15.00 - 17.00", true));
            timeSlotRepository.save(beginningTimeSlot);
            schedule.setDayOfWeek(scheduleSaveRequest.dayOfWeek());
            schedule.setVeterinary(scheduleSaveRequest.veterinary());
            schedule.setTimeSlot(beginningTimeSlot);
            scheduleRepository.save(schedule);
        }
    }
    public MessageResponse updateSchedule(Long scheduleId, ScheduleUpdateRequest scheduleUpdateRequest) {
        var schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with given ID!"));
        modelMapper.map(scheduleUpdateRequest, schedule);
        scheduleRepository.save(schedule);
        return new MessageResponse("Schedule updated successfully");
    }
}
