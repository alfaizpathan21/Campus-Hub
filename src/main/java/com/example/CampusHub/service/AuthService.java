package com.example.CampusHub.service;

import com.example.CampusHub.dto.LoginRequest;
import com.example.CampusHub.dto.OtpVerificationRequest;
import com.example.CampusHub.dto.RegisterRequest;
import com.example.CampusHub.enums.UserRole;
import com.example.CampusHub.exception.EmailAlreadyExistsException;
import com.example.CampusHub.exception.InvalidCredentialsException;
import com.example.CampusHub.exception.InvalidOtpException;
import com.example.CampusHub.exception.UserNotFoundException;
import com.example.CampusHub.model.OtpData;
import com.example.CampusHub.model.User;
import com.example.CampusHub.repository.UserRepository;
import com.example.CampusHub.response.AuthResponse;
import com.example.CampusHub.security.JwtService;
import com.example.CampusHub.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final OtpUtil otpUtil;
    private final EmailService emailService;

    private final Map<String, OtpData> otpStorage = new HashMap<>();

    // =====================================
    // ✅ STUDENT REGISTER (SELF REGISTER)
    // =====================================
    public String register(RegisterRequest request) {

        if (!request.getEmail().endsWith("@ghrcemn.raisoni.net")) {
            throw new IllegalArgumentException("Invalid college email");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .rollNo(request.getRollNo())
                .email(request.getEmail())
                .department(request.getDepartment())
                .section(request.getSection())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.STUDENT)   // 🔥 Force STUDENT
                .verified(false)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        String otp = otpUtil.generateOtp();

        OtpData otpData = new OtpData(
                otp,
                LocalDateTime.now().plusMinutes(5)
        );

        otpStorage.put(request.getEmail(), otpData);

        emailService.sendOtp(request.getEmail(), otp);

        return "OTP sent to email";
    }

    // =====================================
    // ✅ VERIFY OTP
    // =====================================
    public String verifyOtp(OtpVerificationRequest request) {

        OtpData otpData = otpStorage.get(request.getEmail());

        if (otpData == null) {
            throw new InvalidOtpException("OTP not found");
        }

        if (otpData.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpStorage.remove(request.getEmail());
            throw new InvalidOtpException("OTP expired");
        }

        if (!otpData.getOtp().equals(request.getOtp())) {
            throw new InvalidOtpException("Invalid OTP");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setVerified(true);
        userRepository.save(user);

        otpStorage.remove(request.getEmail());

        return "Account verified successfully";
    }

    // =====================================
    // ✅ LOGIN (ALL ROLES)
    // =====================================
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        if (!user.isVerified()) {
            throw new InvalidCredentialsException("Account not verified");
        }

        // 🔥 Include role in JWT claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        String token = jwtService.generateToken( user.getEmail());

        return new AuthResponse(
                token,
                "Login successful",
                user.getRole().name()  // 🔥 send role to frontend
        );
    }

    // =====================================
    // ✅ ADMIN CREATE FACULTY
    // =====================================
    public String createFaculty(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .department(request.getDepartment())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.FACULTY)
                .verified(true)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return "Faculty created successfully";
    }

    // =====================================
    // ✅ ADMIN CREATE DEPARTMENT USER
    // =====================================
    public String createDepartmentUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .department(request.getDepartment())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.DEPARTMENT)
                .verified(true)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return "Department user created successfully";
    }

    // =====================================
    // ✅ RESEND OTP
    // =====================================
    public String resendOtp(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.isVerified()) {
            return "User already verified";
        }

        String otp = otpUtil.generateOtp();

        OtpData otpData = new OtpData(
                otp,
                LocalDateTime.now().plusMinutes(5)
        );

        otpStorage.put(email, otpData);

        emailService.sendOtp(email, otp);

        return "OTP resent successfully";
    }
}