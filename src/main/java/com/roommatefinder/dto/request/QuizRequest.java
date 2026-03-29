package com.roommatefinder.dto.request;

import com.roommatefinder.model.enums.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class QuizRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Sleep schedule is required")
    private SleepSchedule sleepSchedule;

    @NotNull(message = "Cleanliness level is required")
    @Min(value = 1, message = "Cleanliness level must be between 1 and 10")
    @Max(value = 10, message = "Cleanliness level must be between 1 and 10")
    private Integer cleanlinessLevel;

    @NotNull(message = "Noise tolerance is required")
    private NoiseTolerance noiseTolerance;

    @NotNull(message = "Guests frequency is required")
    private Frequency guestsFrequency;

    @NotNull(message = "Smoking preference is required")
    private Boolean smoking;

    @NotNull(message = "Drinking habit is required")
    private DrinkingHabit drinking;

    @NotNull(message = "Pet preference is required")
    private PetPreference pets;

    @NotNull(message = "Cooking frequency is required")
    private Frequency cookingFrequency;

    @NotNull(message = "Social level is required")
    private SocialLevel socialLevel;

    @NotNull(message = "Minimum budget is required")
    @Min(value = 1000, message = "Minimum budget must be at least 1000")
    private Integer budgetMin;

    @NotNull(message = "Maximum budget is required")
    private Integer budgetMax;
}