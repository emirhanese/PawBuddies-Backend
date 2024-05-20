package tr.edu.marmara.petcare.dto;

import java.util.Map;

public record TimeSlotUpdateRequest(Map<String, Boolean> availableHours) {
}
