package com.roommatefinder.controller;

import com.roommatefinder.dto.request.RoommateRequest;
import com.roommatefinder.dto.response.MessageResponse;
import com.roommatefinder.model.RoommateListing;
import com.roommatefinder.service.RoommateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roommates")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RoommateController {

    private final RoommateService roommateService;

    @PostMapping
    public ResponseEntity<RoommateListing> createListing(@Valid @RequestBody RoommateRequest request) {
        RoommateListing listing = roommateService.createListing(request);
        return new ResponseEntity<>(listing, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoommateListing>> searchRoommates(
            @RequestParam(required = false) Integer minBudget,
            @RequestParam(required = false) Integer maxBudget) {
        List<RoommateListing> listings = roommateService.searchRoommates(minBudget, maxBudget);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoommateListing> getListingById(@PathVariable Long id) {
        RoommateListing listing = roommateService.getListingById(id);
        return ResponseEntity.ok(listing);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RoommateListing>> getUserListings(@PathVariable Long userId) {
        List<RoommateListing> listings = roommateService.getUserListings(userId);
        return ResponseEntity.ok(listings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoommateListing> updateListing(
            @PathVariable Long id,
            @Valid @RequestBody RoommateRequest request) {
        RoommateListing listing = roommateService.updateListing(id, request);
        return ResponseEntity.ok(listing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteListing(@PathVariable Long id) {
        roommateService.deleteListing(id);
        return ResponseEntity.ok(new MessageResponse("Listing deleted successfully"));
    }
}