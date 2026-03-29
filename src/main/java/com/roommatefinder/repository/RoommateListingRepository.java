package com.roommatefinder.repository;

import com.roommatefinder.model.RoommateListing;
import com.roommatefinder.model.User;
import com.roommatefinder.model.enums.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoommateListingRepository extends JpaRepository<RoommateListing, Long> {

    List<RoommateListing> findByUser(User user);

    List<RoommateListing> findByStatus(ListingStatus status);

    @Query("SELECT r FROM RoommateListing r WHERE " +
            "r.status = 'ACTIVE' AND " +
            "(:minBudget IS NULL OR r.budgetPerPerson >= :minBudget) AND " +
            "(:maxBudget IS NULL OR r.budgetPerPerson <= :maxBudget)")
    List<RoommateListing> searchRoommates(
            @Param("minBudget") Integer minBudget,
            @Param("maxBudget") Integer maxBudget
    );
}