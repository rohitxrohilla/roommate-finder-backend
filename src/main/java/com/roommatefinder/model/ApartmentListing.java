package com.roommatefinder.model;

import com.roommatefinder.model.enums.BhkType;
import com.roommatefinder.model.enums.FurnishingStatus;
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
@Table(name = "apartment_listings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer rent;

    @Column(nullable = false)
    private Integer deposit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BhkType bhkCount;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(precision = 10, scale = 8)
    private Double latitude;

    @Column(precision = 11, scale = 8)
    private Double longitude;

    @Column(nullable = false)
    private LocalDate availableFrom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FurnishingStatus furnishingStatus;

    @Column(columnDefinition = "JSON")
    private String amenities; // JSON array: ["WiFi", "Parking", "Gym"]

    @Column(columnDefinition = "JSON")
    private String photos; // JSON array: ["url1", "url2"]

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ListingStatus status = ListingStatus.ACTIVE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}