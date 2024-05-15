package tr.edu.marmara.petcare.dto;

import tr.edu.marmara.petcare.model.Pet;
import tr.edu.marmara.petcare.model.User;
import tr.edu.marmara.petcare.model.Veterinary;

public record ReservationSaveRequest(User reservationOwner, Pet pet, Veterinary veterinary,
                                     String reservationDay, String reservationTime, String subject) {
}
