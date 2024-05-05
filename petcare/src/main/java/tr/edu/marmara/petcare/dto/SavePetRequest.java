package tr.edu.marmara.petcare.dto;

import java.util.Date;

public record SavePetRequest(String name, String type, String gender, String image, String specialInfo,
                             Double weight, Double height, Date birthDate) {
}
