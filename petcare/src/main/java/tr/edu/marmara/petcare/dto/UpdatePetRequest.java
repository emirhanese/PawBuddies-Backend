package tr.edu.marmara.petcare.dto;

import java.util.Date;

public record UpdatePetRequest(String name, String type, String genus, String gender,
                               String image, Double weight, Double longitude, Double latitude,
                               Date birthDate) {
}
