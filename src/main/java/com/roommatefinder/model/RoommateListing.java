package com.roommatefinder.model;

import com.roommatefinder.model.enums.Gender;
import com.roommatefinder.model.enums.ListingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "roommate_listings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoommateListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer lookingForCount; // 1-5

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender genderPreference;

    @Column(nullable = false)
    private Integer ageRangeMin;

    @Column(nullable = false)
    private Integer ageRangeMax;

    @Column(nullable = false)
    private Integer budgetPerPerson;

    @Column(nullable = false)
    private LocalDate moveInDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ListingStatus status = ListingStatus.ACTIVE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}