package com.roommatefinder.dto.request;

import com.roommatefinder.model.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RoommateRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Looking for count is required")
    @Min(value = 1, message = "Must be looking for at least 1 roommate")
    @Max(value = 5, message = "Cannot look for more than 5 roommates")
    private Integer lookingForCount;

    @NotNull(message = "Gender preference is required")
    private Gender genderPreference;

    @NotNull(message = "Minimum age is required")
    @Min(value = 18, message = "Minimum age must be at least 18")
    private Integer ageRangeMin;

    @NotNull(message = "Maximum age is required")
    @Max(value = 100, message = "Maximum age cannot exceed 100")
    private Integer ageRangeMax;

    @NotNull(message = "Budget per person is required")
    @Min(value = 1000, message = "Budget must be at least 1000")
    private Integer budgetPerPerson;

    @NotNull(message = "Move-in date is required")
    @Future(message = "Move-in date must be in the future")
    private LocalDate moveInDate;

    private String description;
}