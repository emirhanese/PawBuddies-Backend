package tr.edu.marmara.petcare.dto;

import java.util.Date;

public record UpdatePetRequest(String name, String type, String gender,
                               String image, String specialInfo, Double weight, Double height,
                               Double longitude, Double latitude, Date birthDate) {
}
