package tr.edu.marmara.petcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    @Column(name = "base64_image", columnDefinition = "MEDIUMBLOB")
    @NotBlank(message = "document image base64 string cannot be null!")
    private String base64Document;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "veterinary_id", nullable = false)
    private User veterinary;
}
