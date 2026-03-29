package com.roommatefinder.service;

import com.roommatefinder.dto.request.RoommateRequest;
import com.roommatefinder.exception.ResourceNotFoundException;
import com.roommatefinder.model.RoommateListing;
import com.roommatefinder.model.User;
import com.roommatefinder.model.enums.ListingStatus;
import com.roommatefinder.repository.RoommateListingRepository;
import com.roommatefinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoommateService {

    private final RoommateListingRepository roommateRepository;
    private final UserRepository userRepository;

    @Transactional
    public RoommateListing createListing(RoommateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        RoommateListing listing = new RoommateListing();
        listing.setUser(user);
        listing.setLookingForCount(request.getLookingForCount());
        listing.setGenderPreference(request.getGenderPreference());
        listing.setAgeRangeMin(request.getAgeRangeMin());
        listing.setAgeRangeMax(request.getAgeRangeMax());
        listing.setBudgetPerPerson(request.getBudgetPerPerson());
        listing.setMoveInDate(request.getMoveInDate());
        listing.setDescription(request.getDescription());
        listing.setStatus(ListingStatus.ACTIVE);

        return roommateRepository.save(listing);
    }

    public List<RoommateListing> searchRoommates(Integer minBudget, Integer maxBudget) {
        return roommateRepository.searchRoommates(minBudget, maxBudget);
    }

    public RoommateListing getListingById(Long listingId) {
        return roommateRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Roommate listing not found"));
    }

    public List<RoommateListing> getUserListings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return roommateRepository.findByUser(user);
    }

    @Transactional
    public RoommateListing updateListing(Long listingId, RoommateRequest request) {
        RoommateListing listing = roommateRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Roommate listing not found"));

        if (request.getLookingForCount() != null) {
            listing.setLookingForCount(request.getLookingForCount());
        }
        if (request.getGenderPreference() != null) {
            listing.setGenderPreference(request.getGenderPreference());
        }
        if (request.getAgeRangeMin() != null) {
            listing.setAgeRangeMin(request.getAgeRangeMin());
        }
        if (request.getAgeRangeMax() != null) {
            listing.setAgeRangeMax(request.getAgeRangeMax());
        }
        if (request.getBudgetPerPerson() != null) {
            listing.setBudgetPerPerson(request.getBudgetPerPerson());
        }
        if (request.getMoveInDate() != null) {
            listing.setMoveInDate(request.getMoveInDate());
        }
        if (request.getDescription() != null) {
            listing.setDescription(request.getDescription());
        }

        return roommateRepository.save(listing);
    }

    @Transactional
    public void deleteListing(Long listingId) {
        RoommateListing listing = roommateRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Roommate listing not found"));

        roommateRepository.delete(listing);
    }
}