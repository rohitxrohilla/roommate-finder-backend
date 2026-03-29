package com.roommatefinder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roommatefinder.dto.request.ApartmentRequest;
import com.roommatefinder.exception.ResourceNotFoundException;
import com.roommatefinder.model.ApartmentListing;
import com.roommatefinder.model.User;
import com.roommatefinder.model.enums.ListingStatus;
import com.roommatefinder.repository.ApartmentListingRepository;
import com.roommatefinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final ApartmentListingRepository apartmentRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public ApartmentListing createListing(ApartmentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        ApartmentListing listing = new ApartmentListing();
        listing.setUser(user);
        listing.setTitle(request.getTitle());
        listing.setDescription(request.getDescription());
        listing.setRent(request.getRent());
        listing.setDeposit(request.getDeposit());
        listing.setBhkCount(request.getBhkCount());
        listing.setAddress(request.getAddress());
        listing.setLatitude(request.getLatitude());
        listing.setLongitude(request.getLongitude());
        listing.setAvailableFrom(request.getAvailableFrom());
        listing.setFurnishingStatus(request.getFurnishingStatus());

        // Convert lists to JSON strings
        try {
            if (request.getAmenities() != null) {
                listing.setAmenities(objectMapper.writeValueAsString(request.getAmenities()));
            }
            if (request.getPhotos() != null) {
                listing.setPhotos(objectMapper.writeValueAsString(request.getPhotos()));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON data", e);
        }

        listing.setStatus(ListingStatus.ACTIVE);

        return apartmentRepository.save(listing);
    }

    public List<ApartmentListing> searchApartments(String location, Integer minRent, Integer maxRent) {
        return apartmentRepository.searchApartments(location, minRent, maxRent);
    }

    public ApartmentListing getListingById(Long listingId) {
        return apartmentRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment listing not found"));
    }

    public List<ApartmentListing> getUserListings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return apartmentRepository.findByUser(user);
    }

    @Transactional
    public ApartmentListing updateListing(Long listingId, ApartmentRequest request) {
        ApartmentListing listing = apartmentRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment listing not found"));

        // Update fields
        if (request.getTitle() != null) {
            listing.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            listing.setDescription(request.getDescription());
        }
        if (request.getRent() != null) {
            listing.setRent(request.getRent());
        }
        if (request.getDeposit() != null) {
            listing.setDeposit(request.getDeposit());
        }
        if (request.getBhkCount() != null) {
            listing.setBhkCount(request.getBhkCount());
        }
        if (request.getAddress() != null) {
            listing.setAddress(request.getAddress());
        }
        if (request.getFurnishingStatus() != null) {
            listing.setFurnishingStatus(request.getFurnishingStatus());
        }

        try {
            if (request.getAmenities() != null) {
                listing.setAmenities(objectMapper.writeValueAsString(request.getAmenities()));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing amenities", e);
        }

        return apartmentRepository.save(listing);
    }

    @Transactional
    public void deleteListing(Long listingId) {
        ApartmentListing listing = apartmentRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment listing not found"));

        apartmentRepository.delete(listing);
    }

    @Transactional
    public ApartmentListing updateStatus(Long listingId, ListingStatus status) {
        ApartmentListing listing = apartmentRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment listing not found"));

        listing.setStatus(status);
        return apartmentRepository.save(listing);
    }
}