package com.roommatefinder.controller;

import com.roommatefinder.dto.request.ApartmentRequest;
import com.roommatefinder.dto.response.MessageResponse;
import com.roommatefinder.model.ApartmentListing;
import com.roommatefinder.model.enums.ListingStatus;
import com.roommatefinder.service.ApartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ApartmentController {

    private final ApartmentService apartmentService;

    @PostMapping
    public ResponseEntity<ApartmentListing> createListing(@Valid @RequestBody ApartmentRequest request) {
        ApartmentListing listing = apartmentService.createListing(request);
        return new ResponseEntity<>(listing, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ApartmentListing>> searchApartments(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer minRent,
            @RequestParam(required = false) Integer maxRent) {
        List<ApartmentListing> listings = apartmentService.searchApartments(location, minRent, maxRent);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentListing> getListingById(@PathVariable Long id) {
        ApartmentListing listing = apartmentService.getListingById(id);
        return ResponseEntity.ok(listing);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ApartmentListing>> getUserListings(@PathVariable Long userId) {
        List<ApartmentListing> listings = apartmentService.getUserListings(userId);
        return ResponseEntity.ok(listings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentListing> updateListing(
            @PathVariable Long id,
            @Valid @RequestBody ApartmentRequest request) {
        ApartmentListing listing = apartmentService.updateListing(id, request);
        return ResponseEntity.ok(listing);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApartmentListing> updateStatus(
            @PathVariable Long id,
            @RequestParam ListingStatus status) {
        ApartmentListing listing = apartmentService.updateStatus(id, status);
        return ResponseEntity.ok(listing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteListing(@PathVariable Long id) {
        apartmentService.deleteListing(id);
        return ResponseEntity.ok(new MessageResponse("Listing deleted successfully"));
    }
}