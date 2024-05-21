package tr.edu.marmara.petcare.dto;

import tr.edu.marmara.petcare.model.TimeSlot;
import tr.edu.marmara.petcare.model.User;
import tr.edu.marmara.petcare.model.Veterinary;

import java.time.DayOfWeek;
import java.util.List;

public record ScheduleSaveRequest(User veterinary, DayOfWeek dayOfWeek) {
}
