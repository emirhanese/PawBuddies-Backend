package tr.edu.marmara.petcare.dto;

public record ScheduleUpdateRequest(String workingDays, String workingHours, String availableHours) {
}
