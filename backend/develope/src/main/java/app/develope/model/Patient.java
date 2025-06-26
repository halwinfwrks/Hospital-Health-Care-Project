package app.develope.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("patients")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Patient {
    @Id
    private String id;
    private String userId;
    private String bloodGroup;
    private String medicalRecords;
    private String emergencyContactNumber;
}
