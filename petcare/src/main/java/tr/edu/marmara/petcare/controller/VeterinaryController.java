package tr.edu.marmara.petcare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.marmara.petcare.dto.MessageResponse;
import tr.edu.marmara.petcare.dto.ScheduleUpdateRequest;
import tr.edu.marmara.petcare.dto.VeterinaryResponse;
import tr.edu.marmara.petcare.dto.VeterinaryUpdateRequest;
import tr.edu.marmara.petcare.model.Schedule;
import tr.edu.marmara.petcare.service.ReservationService;
import tr.edu.marmara.petcare.service.ScheduleService;
import tr.edu.marmara.petcare.service.UserService;
import tr.edu.marmara.petcare.service.VeterinaryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/veterinaries")
public class VeterinaryController {
    private final VeterinaryService veterinaryService;
    private final ScheduleService scheduleService;

    public VeterinaryController(VeterinaryService veterinaryService, ScheduleService scheduleService, ReservationService reservationService) {
        this.veterinaryService = veterinaryService;
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<VeterinaryResponse>> getVeterinaries() {
        return new ResponseEntity<>(veterinaryService.getVeterinaries(), HttpStatus.OK);
    }

    @GetMapping("/{veterinaryId}")
    public ResponseEntity<VeterinaryResponse> getVeterinaryById(@PathVariable UUID veterinaryId) {
        return new ResponseEntity<>(veterinaryService.getVeterinaryById(veterinaryId), HttpStatus.OK);
    }

    @PutMapping("/{veterinaryId}")
    public ResponseEntity<VeterinaryResponse> updateVeterinary(@PathVariable UUID veterinaryId,
                                                                    @RequestBody VeterinaryUpdateRequest veterinaryUpdateRequest) {
        return new ResponseEntity<>(veterinaryService.updateVeterinary(veterinaryId, veterinaryUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{veterinaryId}")
    public ResponseEntity<VeterinaryResponse> deleteVeterinary(@PathVariable UUID veterinaryId) {
        return new ResponseEntity<>(veterinaryService.deleteVeterinary(veterinaryId), HttpStatus.OK);
    }

    @GetMapping("/schedules/{veterinaryId}")
    public ResponseEntity<?> getSchedulesOfVeterinary(@PathVariable UUID veterinaryId,
                                                      @RequestParam(required = false) String day) {
        if(day != null) {
            return new ResponseEntity<>(scheduleService.getScheduleOfVeterinaryForSpecificDay(veterinaryId, day), HttpStatus.OK);
        }
        return new ResponseEntity<>(scheduleService.getSchedulesOfVeterinary(veterinaryId), HttpStatus.OK);
    }
    @PutMapping("/schedules")
    public ResponseEntity<MessageResponse> updateSchedule(@RequestParam Long scheduleId,
                                                          @RequestBody ScheduleUpdateRequest scheduleUpdateRequest) {
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, scheduleUpdateRequest), HttpStatus.OK);
    }
}
