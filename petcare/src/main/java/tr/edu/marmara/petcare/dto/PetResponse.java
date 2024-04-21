package tr.edu.marmara.petcare.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class PetResponse {
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String type;
    private String genus;
    private String gender;
    private String image;
    private Double weight;
    private Double longitude;
    private Double latitude;
    private Date birthDate;
}
