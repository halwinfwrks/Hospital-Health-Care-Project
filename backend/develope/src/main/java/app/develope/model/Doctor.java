package app.develope.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    private String id;
    private String userId;
    private String specialization;
    private String qualifications;
    private String department;
}
