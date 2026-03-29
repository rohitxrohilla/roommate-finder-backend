package com.roommatefinder.dto.response;

import com.roommatefinder.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {
    private Long quizId;
    private Long userId;
    private SleepSchedule sleepSchedule;
    private Integer cleanlinessLevel;
    private NoiseTolerance noiseTolerance;
    private Frequency guestsFrequency;
    private Boolean smoking;
    private DrinkingHabit drinking;
    private PetPreference pets;
    private Frequency cookingFrequency;
    private SocialLevel socialLevel;
    private Integer budgetMin;
    private Integer budgetMax;
    private String message;
}