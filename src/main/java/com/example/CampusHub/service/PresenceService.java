package com.example.CampusHub.service;


import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PresenceService {

    // thread-safe set
    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    // ✅ user online
    public void userOnline(String userId) {
        onlineUsers.add(userId);
    }

    // ✅ user offline
    public void userOffline(String userId) {
        onlineUsers.remove(userId);
    }

    // ✅ check status
    public boolean isUserOnline(String userId) {
        return onlineUsers.contains(userId);
    }
}