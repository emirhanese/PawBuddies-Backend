package tr.edu.marmara.petcare.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.edu.marmara.petcare.dto.MessageResponse;
import tr.edu.marmara.petcare.dto.ScheduleSaveRequest;
import tr.edu.marmara.petcare.dto.ScheduleUpdateRequest;
import tr.edu.marmara.petcare.exception.ScheduleNotFoundException;
import tr.edu.marmara.petcare.model.Schedule;
import tr.edu.marmara.petcare.repository.ScheduleRepository;
import tr.edu.marmara.petcare.repository.UserRepository;

import java.util.UUID;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, ModelMapper modelMapper,
                           UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public Schedule getSchedule(UUID veterinaryId) {
        var veterinary = userRepository.findById(veterinaryId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with given ID!"));
        return scheduleRepository.findScheduleByVeterinary(veterinary)
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
        var veterinary = userRepository.findById(veterinaryId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with given ID!"));
            var schedule = scheduleRepository.findScheduleByVeterinary(veterinary)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with given ID!"));
        modelMapper.map(scheduleUpdateRequest, schedule);
        scheduleRepository.save(schedule);
        return new MessageResponse("Schedule updated successfully");
    }
}
