package com.example.CampusHub.util;


public final class Constants {

    private Constants() {
        // prevent instantiation
    }

    // ================= AUTH =================
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER = "Authorization";

    // ================= OTP =================
    public static final int OTP_EXPIRY_MINUTES = 5;
    public static final int OTP_LENGTH = 6;

    // ================= ROLES =================
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STUDENT = "STUDENT";
    public static final String ROLE_DEPARTMENT = "DEPARTMENT";

    // ================= CACHE =================
    public static final String CACHE_FEED = "feed";

    // ================= WEBSOCKET =================
    public static final String WS_NOTIFICATION = "/queue/notifications";
    public static final String WS_MESSAGES = "/queue/messages";
    public static final String WS_TYPING = "/queue/typing";

    // ================= EVENT =================
    public static final int DEFAULT_EVENT_CAPACITY = 100;

}
