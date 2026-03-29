package com.roommatefinder.service;

import com.roommatefinder.dto.request.LoginRequest;
import com.roommatefinder.dto.request.RegisterRequest;
import com.roommatefinder.dto.response.AuthResponse;
import com.roommatefinder.exception.BadRequestException;
import com.roommatefinder.exception.ConflictException;
import com.roommatefinder.exception.ResourceNotFoundException;
import com.roommatefinder.model.User;
import com.roommatefinder.repository.LifestyleQuizRepository;
import com.roommatefinder.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final LifestyleQuizRepository quizRepository;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository,
                       LifestyleQuizRepository quizRepository,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.emailService = emailService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already registered");
        }

        // Create user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword()); // In production, use BCrypt
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setAge(request.getAge());
        user.setGender(request.getGender());
        user.setOccupation(request.getOccupation());
        user.setBio(request.getBio());
        user.setIsVerified(false);

        User savedUser = userRepository.save(user);

        // Send welcome email
        emailService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getFullName());

        // Check if quiz completed
        boolean hasQuiz = quizRepository.existsByUser(savedUser);

        return new AuthResponse(
                savedUser.getUserId(),
                savedUser.getEmail(),
                savedUser.getFullName(),
                hasQuiz,
                savedUser.getIsVerified(),
                "Registration successful. Please complete your lifestyle quiz."
        );
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        // In production, use BCrypt to verify password
        if (!user.getPasswordHash().equals(request.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        // Check if quiz completed
        boolean hasQuiz = quizRepository.existsByUser(user);

        return new AuthResponse(
                user.getUserId(),
                user.getEmail(),
                user.getFullName(),
                hasQuiz,
                user.getIsVerified(),
                "Login successful"
        );
    }
}