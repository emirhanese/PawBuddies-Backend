package tr.edu.marmara.petcare.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tr.edu.marmara.petcare.dto.MessageResponse;
import tr.edu.marmara.petcare.dto.ScheduleSaveRequest;
import tr.edu.marmara.petcare.dto.ScheduleUpdateRequest;
import tr.edu.marmara.petcare.exception.ScheduleNotFoundException;
import tr.edu.marmara.petcare.model.Schedule;
import tr.edu.marmara.petcare.repository.ScheduleRepository;

import java.util.UUID;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    public ScheduleService(ScheduleRepository scheduleRepository, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

    public Schedule getSchedule(UUID veterinaryId) {
        return scheduleRepository.findScheduleByVeterinary(veterinaryId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with given ID!"));
    }

    public MessageResponse saveSchedule(ScheduleSaveRequest scheduleSaveRequest) {
        var schedule = Schedule.builder()
                .availableHours(scheduleSaveRequest.availableHours())
                .veterinary(scheduleSaveRequest.veterinary())
                .workingHours(scheduleSaveRequest.workingHours())
                .workingDays(scheduleSaveRequest.workingDays())
                .build();
        scheduleRepository.save(schedule);
        return new MessageResponse("Schedule created successfully");
    }

    public MessageResponse updateSchedule(UUID veterinaryId, ScheduleUpdateRequest scheduleUpdateRequest) {
        var schedule = scheduleRepository.findScheduleByVeterinary(veterinaryId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with given ID!"));
        modelMapper.map(scheduleUpdateRequest, schedule);
        scheduleRepository.save(schedule);
        return new MessageResponse("Schedule updated successfully");
    }
}
