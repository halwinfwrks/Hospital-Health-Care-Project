package app.develope.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.develope.model.Doctor;
import app.develope.repository.DoctorRepository;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    // ========== CREATE/UPDATE ==========

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // ========== READ ==========
    public Optional<Doctor> getDoctorByUserId(String userId) {
        return doctorRepository.findByUserId(userId);
    }

    public Optional<Doctor> getDoctorById(String id) {
        return doctorRepository.findById(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // ========== DELETE ==========
    public boolean deleteDoctorById(String id) {
        Optional<Doctor> doctor = getDoctorById(id);
        if (doctor.isPresent())
            return false;
        doctorRepository.delete(doctor.get());
        return true;

    }

    public boolean deleteDoctorByUserId(String userId) {
        Optional<Doctor> doctor = getDoctorByUserId(userId);
        if (!doctor.isPresent())
            return false;
        doctorRepository.delete(doctor.get());
        return true;
    }
}
