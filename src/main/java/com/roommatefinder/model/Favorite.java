package com.roommatefinder.model;

import com.roommatefinder.model.enums.ListingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorites",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "listing_id", "listing_type"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long listingId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ListingType listingType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}