package com.example.CampusHub.repository;


import com.example.CampusHub.model.Registration;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistrationRepository
        extends MongoRepository<Registration, String> {

    boolean existsByEventIdAndUserId(String eventId, String userId);

    long countByEventId(String eventId);
}