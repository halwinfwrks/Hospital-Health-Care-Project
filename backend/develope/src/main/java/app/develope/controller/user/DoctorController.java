package app.develope.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.develope.model.Doctor;
import app.develope.model.User;
import app.develope.service.DoctorService;
import app.develope.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@Slf4j
public class DoctorController {
    private static final String TAG = "DoctorController";

    private final UserService userService;
    private final DoctorService doctorService;

    @PostMapping("/add-doctor")
    public ResponseEntity<?> addDoctor(@RequestBody User user, @RequestBody Doctor doctor) {
        if (!userService.userExists(user.getUsername())) {
            log.error("{}: User {} does not exist", TAG, user.getUsername());
            return ResponseEntity.badRequest().body("User does not exist");
        }
        doctor.setUserId(user.getId());
        if (doctorService.getDoctorByUserId(user.getId()).isPresent()) {
            log.error("{}: Doctor with user ID {} already exists", TAG, user.getId());
            return ResponseEntity.badRequest().body("Doctor profile already exists for this user");
        }
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    @PostMapping("/update-doctor")
    public ResponseEntity<?> updateDoctor(@RequestBody Doctor doctor) {
        if (!doctorService.getDoctorById(doctor.getId()).isPresent()) {
            log.error("{}: Doctor with ID {} does not exist", TAG, doctor.getUserId());
            return ResponseEntity.badRequest().body("Doctor does not exist");
        }
        Doctor updatedDoctor = doctorService.updateDoctor(doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    @PostMapping("/delete-doctor")
    public ResponseEntity<?> deleteDoctor(@RequestBody String id) {
        if (!doctorService.getDoctorById(id).isPresent()) {
            log.error("{}: Doctor with ID {} does not exist", TAG, id);
            return ResponseEntity.badRequest().body("Doctor does not exist");
        }
        boolean deleted = doctorService.deleteDoctorById(id);
        if (deleted) {
            return ResponseEntity.ok("Doctor deleted successfully");
        } else {
            return ResponseEntity.status(500).body("Failed to delete doctor");
        }
    }
}
