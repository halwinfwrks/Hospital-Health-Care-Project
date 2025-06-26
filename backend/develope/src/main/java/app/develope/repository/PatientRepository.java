package app.develope.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.develope.model.Patient;

public interface PatientRepository extends MongoRepository<Patient, String>{
   Optional<Patient> findByUserId(String userId);
}
