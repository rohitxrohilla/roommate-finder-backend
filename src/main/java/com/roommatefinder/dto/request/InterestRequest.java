package com.roommatefinder.dto.request;

import com.roommatefinder.model.enums.ListingType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InterestRequest {

    @NotNull(message = "Listing ID is required")
    private Long listingId;

    @NotNull(message = "Listing type is required")
    private ListingType listingType;

    @NotNull(message = "Interested user ID is required")
    private Long interestedUserId;

    private String message;
}