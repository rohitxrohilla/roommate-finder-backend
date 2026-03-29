package com.roommatefinder.model;

import com.roommatefinder.model.enums.InterestStatus;
import com.roommatefinder.model.enums.ListingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "interest_expressed",
        uniqueConstraints = @UniqueConstraint(columnNames = {"interested_user_id", "listing_id", "listing_type"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterestExpressed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestId;

    @Column(nullable = false)
    private Long listingId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ListingType listingType;

    @ManyToOne
    @JoinColumn(name = "interested_user_id", nullable = false)
    private User interestedUser;

    @ManyToOne
    @JoinColumn(name = "listing_owner_id", nullable = false)
    private User listingOwner;

    private Integer compatibilityScore;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InterestStatus status = InterestStatus.PENDING;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}