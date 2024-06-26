package tr.edu.marmara.petcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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
    private String gender;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    private String specialInfo;
    private Double weight;
    private Double height;
    private Double longitude;
    private Double latitude;
    private Date birthDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}
