package com.example.CampusHub.repository;


import com.example.CampusHub.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
}