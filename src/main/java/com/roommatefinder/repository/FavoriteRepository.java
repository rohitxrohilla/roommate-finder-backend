package com.roommatefinder.repository;

import com.roommatefinder.model.Favorite;
import com.roommatefinder.model.User;
import com.roommatefinder.model.enums.ListingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUserOrderByCreatedAtDesc(User user);

    Optional<Favorite> findByUserAndListingIdAndListingType(
            User user, Long listingId, ListingType listingType
    );

    Boolean existsByUserAndListingIdAndListingType(
            User user, Long listingId, ListingType listingType
    );
}