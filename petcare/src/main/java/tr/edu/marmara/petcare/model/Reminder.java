package tr.edu.marmara.petcare.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "reminders")
public class Reminder extends BaseModel {
}
