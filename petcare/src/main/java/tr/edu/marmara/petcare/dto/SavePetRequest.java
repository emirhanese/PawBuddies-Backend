package tr.edu.marmara.petcare.dto;

import java.util.Date;

public record SavePetRequest(String name, String type, String genus, String gender, String image,
                             Double weight, Date birthDate) {
}
