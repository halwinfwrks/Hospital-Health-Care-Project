package app.develope.controller.user;

import app.develope.dto.RegisterPatientRequest;
import app.develope.model.Patient;
import app.develope.service.PatientService;
import app.develope.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final UserService userService;
    private final PatientService patientService;

    // ✅ Thêm mới bệnh nhân
    @PostMapping
    public ResponseEntity<?> addPatient(@RequestBody RegisterPatientRequest request) {
        String username = request.getUser().getUsername();
        Optional<Patient> existing = patientService.getPatientByUserId(request.getUser().getId());

        if (!userService.userExists(username)) {
            log.warn("User '{}' does not exist", username);
            return ResponseEntity.status(404).body("User does not exist");
        }

        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("This user already has a patient profile");
        }

        Patient patient = request.getPatient();
        patient.setUserId(request.getUser().getId());
        return ResponseEntity.ok(patientService.savePatient(patient));
    }

    // ✅ Cập nhật bệnh nhân
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable String id, @RequestBody Patient updatedPatient) {
        Optional<Patient> existing = patientService.getPatientById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(404).body("Patient not found");
        }

        updatedPatient.setId(id);
        return ResponseEntity.ok(patientService.updatePatient(updatedPatient));
    }

    // ✅ Xoá bệnh nhân
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable String id) {
        Optional<Patient> patient = patientService.getPatientById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.status(404).body("Patient not found");
        }

        boolean deleted = patientService.deletePatientById(id);
        return deleted
                ? ResponseEntity.ok("Patient deleted successfully")
                : ResponseEntity.status(500).body("Failed to delete patient");
    }

    // ✅ Lấy thông tin bệnh nhân theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatient(@PathVariable String id) {
        return patientService.getPatientById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Patient not found"));
    }

    // ✅ Lấy danh sách tất cả bệnh nhân
    @GetMapping
    public ResponseEntity<?> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }
}
