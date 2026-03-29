package com.roommatefinder.controller;

import com.roommatefinder.repository.ApartmentListingRepository;
import com.roommatefinder.repository.FavoriteRepository;
import com.roommatefinder.repository.InterestRepository;
import com.roommatefinder.repository.UserRepository;
import com.roommatefinder.exception.ResourceNotFoundException;
import com.roommatefinder.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final UserRepository userRepository;
    private final ApartmentListingRepository apartmentRepository;
    private final InterestRepository interestRepository;
    private final FavoriteRepository favoriteRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getDashboard(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Map<String, Object> dashboard = new HashMap<>();

        // User info
        dashboard.put("userId", user.getUserId());
        dashboard.put("userName", user.getFullName());
        dashboard.put("isVerified", user.getIsVerified());

        // Stats
        Map<String, Object> stats = new HashMap<>();
        stats.put("activeListingsCount", apartmentRepository.findByUser(user).size());
        stats.put("interestReceivedCount", interestRepository.findByListingOwnerOrderByCreatedAtDesc(user).size());
        stats.put("interestSentCount", interestRepository.findByInterestedUserOrderByCreatedAtDesc(user).size());
        stats.put("favoritesCount", favoriteRepository.findByUserOrderByCreatedAtDesc(user).size());

        dashboard.put("stats", stats);

        // Recent interests (last 5)
        dashboard.put("recentInterests",
                interestRepository.findByListingOwnerOrderByCreatedAtDesc(user)
                        .stream()
                        .limit(5)
                        .toList()
        );

        return ResponseEntity.ok(dashboard);
    }
}