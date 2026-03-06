package com.example.CampusHub.controller;


import com.example.CampusHub.dto.CreateEventRequest;
import com.example.CampusHub.model.Event;
import com.example.CampusHub.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // ✅ create event
    @PostMapping
    public Event createEvent(@RequestBody CreateEventRequest request) {
        return eventService.createEvent(request);
    }

    // ✅ register
    @PostMapping("/register/{eventId}")
    public String register(@PathVariable String eventId) {
        return eventService.registerForEvent(eventId);
    }

    // ✅ get all
    @GetMapping
    public List<Event> getAll() {
        return eventService.getAllEvents();
    }

    // ✅ department events
    @GetMapping("/department/{dept}")
    public List<Event> byDepartment(@PathVariable String dept) {
        return eventService.getDepartmentEvents(dept);
    }

    // ✅ registered students
    @GetMapping("/{eventId}/students")
    public List<String> students(@PathVariable String eventId) {
        return eventService.getRegisteredStudents(eventId);
    }
}