package com.roommatefinder.controller;

import com.roommatefinder.dto.response.CompatibilityResponse;
import com.roommatefinder.service.CompatibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compatibility")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CompatibilityController {

    private final CompatibilityService compatibilityService;

    @GetMapping
    public ResponseEntity<CompatibilityResponse> calculateCompatibility(
            @RequestParam Long user1,
            @RequestParam Long user2) {
        CompatibilityResponse response = compatibilityService.calculateCompatibility(user1, user2);
        return ResponseEntity.ok(response);
    }
}