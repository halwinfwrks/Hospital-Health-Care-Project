package app.develope.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.develope.model.Patient;
import app.develope.repository.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // ========== CREATE/UPDATE ==========

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // ========== READ ==========

    public Optional<Patient> getPatientById(String id) {
        return patientRepository.findById(id);
    }

    public Optional<Patient> getPatientByUserId(String userId) {
        return patientRepository.findByUserId(userId);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // ========== DELETE ==========

    @Transactional
    public boolean deletePatientById(String id) {
    Optional<Patient> patient = patientRepository.findById(id);
        if (!patient.isPresent()) return false;
        patientRepository.delete(patient.get());
        return true;
    }

    @Transactional
    public boolean deletePatientByUserId(String userId) {
        Optional<Patient> patient = patientRepository.findByUserId(userId);
        if (patient.isPresent()) {
            patientRepository.deleteById(patient.get().getId());
            return true;
        }
        return false;
    }

}
