package com.roommatefinder.service;

import com.roommatefinder.dto.request.QuizRequest;
import com.roommatefinder.dto.response.QuizResponse;
import com.roommatefinder.exception.BadRequestException;
import com.roommatefinder.exception.ConflictException;
import com.roommatefinder.exception.ResourceNotFoundException;
import com.roommatefinder.model.LifestyleQuiz;
import com.roommatefinder.model.User;
import com.roommatefinder.repository.LifestyleQuizRepository;
import com.roommatefinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final LifestyleQuizRepository quizRepository;
    private final UserRepository userRepository;

    @Transactional
    public QuizResponse submitQuiz(QuizRequest request) {
        // Validate budget
        if (request.getBudgetMax() <= request.getBudgetMin()) {
            throw new BadRequestException("Maximum budget must be greater than minimum budget");
        }

        // Get user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if quiz already exists
        if (quizRepository.existsByUser(user)) {
            throw new ConflictException("Quiz already submitted. Use update endpoint to modify.");
        }

        // Create quiz
        LifestyleQuiz quiz = new LifestyleQuiz();
        quiz.setUser(user);
        quiz.setSleepSchedule(request.getSleepSchedule());
        quiz.setCleanlinessLevel(request.getCleanlinessLevel());
        quiz.setNoiseTolerance(request.getNoiseTolerance());
        quiz.setGuestsFrequency(request.getGuestsFrequency());
        quiz.setSmoking(request.getSmoking());
        quiz.setDrinking(request.getDrinking());
        quiz.setPets(request.getPets());
        quiz.setCookingFrequency(request.getCookingFrequency());
        quiz.setSocialLevel(request.getSocialLevel());
        quiz.setBudgetMin(request.getBudgetMin());
        quiz.setBudgetMax(request.getBudgetMax());

        LifestyleQuiz saved = quizRepository.save(quiz);

        return mapToResponse(saved, "Quiz submitted successfully");
    }

    public QuizResponse getQuizByUserId(Long userId) {
        LifestyleQuiz quiz = quizRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found for user id: " + userId));

        return mapToResponse(quiz, null);
    }

    @Transactional
    public QuizResponse updateQuiz(Long quizId, QuizRequest request) {
        LifestyleQuiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));

        // Validate budget
        if (request.getBudgetMax() <= request.getBudgetMin()) {
            throw new BadRequestException("Maximum budget must be greater than minimum budget");
        }

        // Update fields
        quiz.setSleepSchedule(request.getSleepSchedule());
        quiz.setCleanlinessLevel(request.getCleanlinessLevel());
        quiz.setNoiseTolerance(request.getNoiseTolerance());
        quiz.setGuestsFrequency(request.getGuestsFrequency());
        quiz.setSmoking(request.getSmoking());
        quiz.setDrinking(request.getDrinking());
        quiz.setPets(request.getPets());
        quiz.setCookingFrequency(request.getCookingFrequency());
        quiz.setSocialLevel(request.getSocialLevel());
        quiz.setBudgetMin(request.getBudgetMin());
        quiz.setBudgetMax(request.getBudgetMax());

        LifestyleQuiz saved = quizRepository.save(quiz);

        return mapToResponse(saved, "Quiz updated successfully");
    }

    private QuizResponse mapToResponse(LifestyleQuiz quiz, String message) {
        return new QuizResponse(
                quiz.getQuizId(),
                quiz.getUser().getUserId(),
                quiz.getSleepSchedule(),
                quiz.getCleanlinessLevel(),
                quiz.getNoiseTolerance(),
                quiz.getGuestsFrequency(),
                quiz.getSmoking(),
                quiz.getDrinking(),
                quiz.getPets(),
                quiz.getCookingFrequency(),
                quiz.getSocialLevel(),
                quiz.getBudgetMin(),
                quiz.getBudgetMax(),
                message
        );
    }
}