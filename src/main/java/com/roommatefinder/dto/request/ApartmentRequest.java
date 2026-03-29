package com.roommatefinder.dto.request;

import com.roommatefinder.model.enums.BhkType;
import com.roommatefinder.model.enums.FurnishingStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ApartmentRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Title is required")
    @Size(min = 10, max = 200)
    private String title;

    private String description;

    @NotNull(message = "Rent is required")
    @Min(value = 1000, message = "Rent must be at least 1000")
    private Integer rent;

    @NotNull(message = "Deposit is required")
    @Min(value = 0, message = "Deposit cannot be negative")
    private Integer deposit;

    @NotNull(message = "BHK type is required")
    private BhkType bhkCount;

    @NotBlank(message = "Address is required")
    private String address;

    private Double latitude;

    private Double longitude;

    @NotNull(message = "Available from date is required")
    @Future(message = "Available from date must be in the future")
    private LocalDate availableFrom;

    @NotNull(message = "Furnishing status is required")
    private FurnishingStatus furnishingStatus;

    private List<String> amenities;

    private List<String> photos;
}