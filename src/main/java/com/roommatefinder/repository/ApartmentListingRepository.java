package com.roommatefinder.repository;

import com.roommatefinder.model.ApartmentListing;
import com.roommatefinder.model.User;
import com.roommatefinder.model.enums.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentListingRepository extends JpaRepository<ApartmentListing, Long> {

    List<ApartmentListing> findByUser(User user);

    List<ApartmentListing> findByStatus(ListingStatus status);

    @Query("SELECT a FROM ApartmentListing a WHERE " +
            "a.status = 'ACTIVE' AND " +
            "(:location IS NULL OR LOWER(a.address) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:minRent IS NULL OR a.rent >= :minRent) AND " +
            "(:maxRent IS NULL OR a.rent <= :maxRent)")
    List<ApartmentListing> searchApartments(
            @Param("location") String location,
            @Param("minRent") Integer minRent,
            @Param("maxRent") Integer maxRent
    );
}