package tr.edu.marmara.petcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address extends BaseModel {
    private String phoneNumber;
    private String clinicName;
    private String clinicCity;
    private String clinicDistrict;
    private String clinicStreet;
    private String clinicNo;
    private String apartmentNo;
    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "veterinary_id")
    private User veterinary;
}
