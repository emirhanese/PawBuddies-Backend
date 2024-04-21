package tr.edu.marmara.petcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "pets")
public class Pet extends BaseModel {
    private String name;
    private String type;
    private String genus;
    private String gender;
    private String image;
    private Double weight;
    private Double longitude;
    private Double latitude;
    private Date birthDate;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User owner;
}
