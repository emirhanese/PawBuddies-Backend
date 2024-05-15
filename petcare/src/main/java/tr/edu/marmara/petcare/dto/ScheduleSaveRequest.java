package tr.edu.marmara.petcare.dto;

import tr.edu.marmara.petcare.model.Veterinary;

public record ScheduleSaveRequest(Veterinary veterinary, String workingDays, String workingHours,
                                  String availableHours) {
}
