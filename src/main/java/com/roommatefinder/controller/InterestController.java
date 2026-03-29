package com.roommatefinder.controller;

import com.roommatefinder.dto.request.InterestRequest;
import com.roommatefinder.model.InterestExpressed;
import com.roommatefinder.service.InterestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interest")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InterestController {

    private final InterestService interestService;

    @PostMapping
    public ResponseEntity<InterestExpressed> expressInterest(@Valid @RequestBody InterestRequest request) {
        InterestExpressed interest = interestService.expressInterest(request);
        return new ResponseEntity<>(interest, HttpStatus.CREATED);
    }

    @GetMapping("/received")
    public ResponseEntity<List<InterestExpressed>> getReceivedInterests(@RequestParam Long userId) {
        List<InterestExpressed> interests = interestService.getReceivedInterests(userId);
        return ResponseEntity.ok(interests);
    }

    @GetMapping("/sent")
    public ResponseEntity<List<InterestExpressed>> getSentInterests(@RequestParam Long userId) {
        List<InterestExpressed> interests = interestService.getSentInterests(userId);
        return ResponseEntity.ok(interests);
    }
}