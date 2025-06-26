package app.develope.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private String roles;

    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String address;
    private String profilePictureUrl;
    private String bio;
    private boolean isActive;
    private String createdAt;
    private String updatedAt;

    private String remeberToken;
    private String resetPasswordToken;
}
