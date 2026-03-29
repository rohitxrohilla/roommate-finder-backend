package com.roommatefinder.dto.request;

import com.roommatefinder.model.enums.ListingType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Listing ID is required")
    private Long listingId;

    @NotNull(message = "Listing type is required")
    private ListingType listingType;
}