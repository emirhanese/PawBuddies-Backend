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
    private String gender;
    private String image;
    private String specialInfo;
    private Double weight;
    private Double height;
    private Double longitude;
    private Double latitude;
    private Date birthDate;
}
