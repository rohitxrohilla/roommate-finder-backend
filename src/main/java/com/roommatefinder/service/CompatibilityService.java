package com.roommatefinder.service;

import com.roommatefinder.dto.response.CompatibilityResponse;
import com.roommatefinder.exception.ResourceNotFoundException;
import com.roommatefinder.model.LifestyleQuiz;
import com.roommatefinder.repository.LifestyleQuizRepository;
import com.roommatefinder.util.CompatibilityCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CompatibilityService {

    private final LifestyleQuizRepository quizRepository;
    private final CompatibilityCalculator calculator;

    public CompatibilityResponse calculateCompatibility(Long user1Id, Long user2Id) {
        // Get both quizzes
        LifestyleQuiz quiz1 = quizRepository.findByUser_UserId(user1Id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found for user " + user1Id));

        LifestyleQuiz quiz2 = quizRepository.findByUser_UserId(user2Id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found for user " + user2Id));

        // Calculate score
        int score = calculator.calculate(quiz1, quiz2);

        // Get detailed breakdown
        Map<String, Object> breakdown = calculator.getDetailedBreakdown(quiz1, quiz2);

        // Get summary and recommendation
        String summary = calculator.getSummary(score, quiz1, quiz2);
        String recommendation = calculator.getRecommendation(score);

        return new CompatibilityResponse(
                user1Id,
                user2Id,
                score,
                breakdown,
                summary,
                recommendation
        );
    }

    public int calculateScore(Long user1Id, Long user2Id) {
        LifestyleQuiz quiz1 = quizRepository.findByUser_UserId(user1Id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found for user " + user1Id));

        LifestyleQuiz quiz2 = quizRepository.findByUser_UserId(user2Id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found for user " + user2Id));

        return calculator.calculate(quiz1, quiz2);
    }
}