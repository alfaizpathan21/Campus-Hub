package com.example.CampusHub.service;


import com.example.CampusHub.dto.CreateEventRequest;
import com.example.CampusHub.model.Event;
import com.example.CampusHub.model.User;
import com.example.CampusHub.repository.EventRepository;
import com.example.CampusHub.service.NotificationService;
import com.example.CampusHub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.CampusHub.enums.NotificationType;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    // ✅ Create event
    public Event createEvent(CreateEventRequest request) {

        User creator = userService.getCurrentUser();

        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .department(request.getDepartment())
                .eventDate(request.getEventDate())
                .createdBy(creator.getId())
                .createdAt(LocalDateTime.now())
                .build();

        return eventRepository.save(event);
    }

    // ✅ Register student
    public String registerForEvent(String eventId) {

        User user = userService.getCurrentUser();

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // prevent duplicate
        if (event.getRegisteredStudents().contains(user.getId())) {
            return "Already registered";
        }

        event.getRegisteredStudents().add(user.getId());
        eventRepository.save(event);

        // 🔥 notify creator
        notificationService.createNotification(
                user.getId(),
                event.getCreatedBy(),
                NotificationType.EVENT,
                "registered for your event"
        );


        return "Registered successfully";
    }

    // ✅ Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAllByOrderByEventDateAsc();
    }

    // ✅ Get department events
    public List<Event> getDepartmentEvents(String department) {
        return eventRepository.findByDepartmentOrderByEventDateAsc(department);
    }

    // ✅ Get registered students
    public List<String> getRegisteredStudents(String eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return event.getRegisteredStudents();
    }
}