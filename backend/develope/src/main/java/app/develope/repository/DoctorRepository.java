package app.develope.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.develope.model.Doctor;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String>{
    Optional<Doctor> findByUserId(String userId);
}
