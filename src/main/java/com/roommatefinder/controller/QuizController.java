package com.roommatefinder.controller;

import com.roommatefinder.dto.request.QuizRequest;
import com.roommatefinder.dto.response.QuizResponse;
import com.roommatefinder.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<QuizResponse> submitQuiz(@Valid @RequestBody QuizRequest request) {
        QuizResponse response = quizService.submitQuiz(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<QuizResponse> getQuizByUserId(@PathVariable Long userId) {
        QuizResponse response = quizService.getQuizByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{quizId}")
    public ResponseEntity<QuizResponse> updateQuiz(
            @PathVariable Long quizId,
            @Valid @RequestBody QuizRequest request) {
        QuizResponse response = quizService.updateQuiz(quizId, request);
        return ResponseEntity.ok(response);
    }
}