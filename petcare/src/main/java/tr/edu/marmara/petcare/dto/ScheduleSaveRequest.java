package tr.edu.marmara.petcare.dto;

import tr.edu.marmara.petcare.model.User;

import java.time.DayOfWeek;
import java.util.Map;

public record ScheduleSaveRequest(User veterinary, DayOfWeek dayOfWeek, Map<String, Boolean> availableHours) {
}
