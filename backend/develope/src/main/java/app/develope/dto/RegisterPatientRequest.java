package app.develope.dto;

import app.develope.model.Patient;
import app.develope.model.User;
import lombok.Data;

@Data
public class RegisterPatientRequest {
    private User user;
    private Patient patient;
}
