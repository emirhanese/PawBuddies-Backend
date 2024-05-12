package tr.edu.marmara.petcare.dto;

import tr.edu.marmara.petcare.model.Address;
import tr.edu.marmara.petcare.model.Document;
import tr.edu.marmara.petcare.model.UserRole;

import java.util.Date;
import java.util.UUID;

public record VeterinaryResponse(UUID veterinaryId, Date createdAt, Date updatedAt, String name,
                                 String surname, String email, UserRole role, Address address, Document document) {
}
