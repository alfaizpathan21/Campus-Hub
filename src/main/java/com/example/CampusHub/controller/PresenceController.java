package com.example.CampusHub.controller;


import com.example.CampusHub.service.PresenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/presence")
@RequiredArgsConstructor
public class PresenceController {

    private final PresenceService presenceService;

    @GetMapping("/{userId}")
    public boolean isOnline(@PathVariable String userId) {
        return presenceService.isUserOnline(userId);
    }
}
