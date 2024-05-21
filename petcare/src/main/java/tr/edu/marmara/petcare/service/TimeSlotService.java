package tr.edu.marmara.petcare.service;

import org.springframework.stereotype.Service;
import tr.edu.marmara.petcare.dto.MessageResponse;
import tr.edu.marmara.petcare.dto.TimeSlotUpdateRequest;
import tr.edu.marmara.petcare.exception.ScheduleNotFoundException;
import tr.edu.marmara.petcare.model.TimeSlot;
import tr.edu.marmara.petcare.repository.ScheduleRepository;
import tr.edu.marmara.petcare.repository.TimeSlotRepository;

import java.util.List;

@Service
public class TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;
    private final ScheduleRepository scheduleRepository;

    public TimeSlotService(TimeSlotRepository timeSlotRepository, ScheduleRepository scheduleRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<TimeSlot> getTimeSlotsByScheduleId(Long scheduleId) {
        var schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found!"));
        return timeSlotRepository.findAllBySchedule(schedule)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found!"));
    }

    public void saveTimeSlot(TimeSlot timeSlot) {
        timeSlotRepository.save(timeSlot);
    }

    public MessageResponse updateTimeSlotsOfSchedule(Long scheduleId, TimeSlotUpdateRequest timeSlotUpdateRequest) {
        var schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found!"));
        var oldTimeSlot = timeSlotRepository.findBySchedule(schedule)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found!"));
        oldTimeSlot.setAvailableHours(timeSlotUpdateRequest.availableHours());
        timeSlotRepository.save(oldTimeSlot);
        return new MessageResponse("Time slot has been updated successfully.");
    }
}
