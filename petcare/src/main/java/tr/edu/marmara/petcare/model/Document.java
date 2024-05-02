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
    @NotBlank(message = "document image base64 string cannot be null!")
    @Column(name = "base64_image")
    private String base64Document;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "veterinary_id", nullable = false)
    private User veterinary;
}
