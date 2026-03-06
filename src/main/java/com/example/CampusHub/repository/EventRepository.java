package com.example.CampusHub.repository;


import com.example.CampusHub.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findAllByOrderByEventDateAsc();

    List<Event> findByDepartmentOrderByEventDateAsc(String department);
}