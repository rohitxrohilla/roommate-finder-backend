package com.roommatefinder.service;

import com.roommatefinder.dto.request.InterestRequest;
import com.roommatefinder.exception.BadRequestException;
import com.roommatefinder.exception.ConflictException;
import com.roommatefinder.exception.ResourceNotFoundException;
import com.roommatefinder.model.ApartmentListing;
import com.roommatefinder.model.InterestExpressed;
import com.roommatefinder.model.RoommateListing;
import com.roommatefinder.model.User;
import com.roommatefinder.model.enums.ListingType;
import com.roommatefinder.repository.ApartmentListingRepository;
import com.roommatefinder.repository.InterestRepository;
import com.roommatefinder.repository.RoommateListingRepository;
import com.roommatefinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final UserRepository userRepository;
    private final ApartmentListingRepository apartmentRepository;
    private final RoommateListingRepository roommateRepository;
    private final CompatibilityService compatibilityService;
    private final EmailService emailService;

    @Transactional
    public InterestExpressed expressInterest(InterestRequest request) {
        // Get interested user
        User interestedUser = userRepository.findById(request.getInterestedUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Get listing owner
        User listingOwner = getListingOwner(request.getListingId(), request.getListingType());

        // Check if already expressed interest
        if (interestRepository.existsByInterestedUserAndListingIdAndListingType(
                interestedUser, request.getListingId(), request.getListingType())) {
            throw new ConflictException("You have already expressed interest in this listing");
        }

        // Calculate compatibility score (only for roommate listings)
        Integer compatibilityScore = null;
        if (request.getListingType() == ListingType.ROOMMATE) {
            try {
                compatibilityScore = compatibilityService.calculateScore(
                        interestedUser.getUserId(),
                        listingOwner.getUserId()
                );
            } catch (Exception e) {
                // If one user hasn't completed quiz, score will be null
                compatibilityScore = null;
            }
        }

        // Create interest
        InterestExpressed interest = new InterestExpressed();
        interest.setListingId(request.getListingId());
        interest.setListingType(request.getListingType());
        interest.setInterestedUser(interestedUser);
        interest.setListingOwner(listingOwner);
        interest.setCompatibilityScore(compatibilityScore);
        interest.setMessage(request.getMessage());

        InterestExpressed saved = interestRepository.save(interest);

        // Send email notification to listing owner
        String listingTitle = getListingTitle(request.getListingId(), request.getListingType());
        emailService.sendInterestNotification(
                listingOwner.getEmail(),
                listingOwner.getFullName(),
                interestedUser.getFullName(),
                interestedUser.getEmail(),
                interestedUser.getPhone(),
                listingTitle,
                compatibilityScore,
                request.getMessage()
        );

        // Send confirmation email to interested user
        emailService.sendInterestConfirmation(
                interestedUser.getEmail(),
                interestedUser.getFullName(),
                listingOwner.getEmail(),
                listingOwner.getPhone(),
                listingTitle
        );

        return saved;
    }

    public List<InterestExpressed> getReceivedInterests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return interestRepository.findByListingOwnerOrderByCreatedAtDesc(user);
    }

    public List<InterestExpressed> getSentInterests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return interestRepository.findByInterestedUserOrderByCreatedAtDesc(user);
    }

    private User getListingOwner(Long listingId, ListingType listingType) {
        if (listingType == ListingType.APARTMENT) {
            ApartmentListing listing = apartmentRepository.findById(listingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Apartment listing not found"));
            return listing.getUser();
        } else {
            RoommateListing listing = roommateRepository.findById(listingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Roommate listing not found"));
            return listing.getUser();
        }
    }

    private String getListingTitle(Long listingId, ListingType listingType) {
        if (listingType == ListingType.APARTMENT) {
            ApartmentListing listing = apartmentRepository.findById(listingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Apartment listing not found"));
            return listing.getTitle();
        } else {
            RoommateListing listing = roommateRepository.findById(listingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Roommate listing not found"));
            User owner = listing.getUser();
            return "Roommate listing by " + owner.getFullName();
        }
    }
}