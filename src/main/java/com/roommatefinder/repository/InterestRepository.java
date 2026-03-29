package com.roommatefinder.repository;

import com.roommatefinder.model.InterestExpressed;
import com.roommatefinder.model.User;
import com.roommatefinder.model.enums.ListingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<InterestExpressed, Long> {

    List<InterestExpressed> findByListingOwnerOrderByCreatedAtDesc(User listingOwner);

    List<InterestExpressed> findByInterestedUserOrderByCreatedAtDesc(User interestedUser);

    Optional<InterestExpressed> findByInterestedUserAndListingIdAndListingType(
            User interestedUser, Long listingId, ListingType listingType
    );

    Boolean existsByInterestedUserAndListingIdAndListingType(
            User interestedUser, Long listingId, ListingType listingType
    );
}