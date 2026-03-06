package com.example.CampusHub.controller;

import com.example.CampusHub.dto.EmailRequest;
import com.example.CampusHub.dto.LoginRequest;
import com.example.CampusHub.dto.OtpVerificationRequest;
import com.example.CampusHub.dto.RegisterRequest;
import com.example.CampusHub.response.AuthResponse;
import com.example.CampusHub.service.AuthService;
import com.example.CampusHub.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    // ===============================
    // ✅ REGISTER (send OTP)
    // ===============================
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    // ===============================
    // ✅ VERIFY OTP
    // ===============================
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody OtpVerificationRequest request) {
        return authService.verifyOtp(request);
    }

    // ===============================
    // ✅ LOGIN
    // ===============================
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    // ===============================
    // ✅ RESEND OTP (VERY IMPORTANT)
    // ===============================
    @PostMapping("/resend-otp")
    public String resendOtp(@RequestBody EmailRequest request) {
        return authService.resendOtp(request.getEmail());
    }


    // ===============================
    // 🧪 TEST MAIL (DEV ONLY)
    // ===============================
    @PostMapping("/test-mail")
    public String testMail(@RequestBody EmailRequest request) {
        emailService.sendOtp(request.getEmail(), "123456");
        return "Test mail sent successfully";
    }

}
