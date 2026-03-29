package com.roommatefinder.util;

import com.roommatefinder.model.LifestyleQuiz;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CompatibilityCalculator {

    // Weight constants
    private static final int DEALBREAKER_WEIGHT = 30;
    private static final int DAILY_HABITS_WEIGHT = 40;
    private static final int LIFESTYLE_WEIGHT = 30;

    /**
     * Calculate compatibility score between two users
     */
    public int calculate(LifestyleQuiz quiz1, LifestyleQuiz quiz2) {
        int dealbreakersScore = calculateDealbreakers(quiz1, quiz2);
        int dailyHabitsScore = calculateDailyHabits(quiz1, quiz2);
        int lifestyleScore = calculateLifestyle(quiz1, quiz2);

        int totalScore = dealbreakersScore + dailyHabitsScore + lifestyleScore;
        return Math.min(totalScore, 100); // Cap at 100
    }

    /**
     * Get detailed breakdown of compatibility
     */
    public Map<String, Object> getDetailedBreakdown(LifestyleQuiz quiz1, LifestyleQuiz quiz2) {
        Map<String, Object> breakdown = new HashMap<>();

        // Dealbreakers
        Map<String, Object> dealbreakers = new HashMap<>();
        dealbreakers.put("score", calculateDealbreakers(quiz1, quiz2));
        dealbreakers.put("maxScore", DEALBREAKER_WEIGHT);
        dealbreakers.put("smoking", Map.of(
                "match", quiz1.getSmoking().equals(quiz2.getSmoking()),
                "user1", quiz1.getSmoking(),
                "user2", quiz2.getSmoking()
        ));
        dealbreakers.put("pets", Map.of(
                "match", quiz1.getPets().equals(quiz2.getPets()),
                "user1", quiz1.getPets(),
                "user2", quiz2.getPets()
        ));
        dealbreakers.put("drinking", Map.of(
                "match", quiz1.getDrinking().equals(quiz2.getDrinking()),
                "user1", quiz1.getDrinking(),
                "user2", quiz2.getDrinking()
        ));
        breakdown.put("dealbreakers", dealbreakers);

        // Daily Habits
        Map<String, Object> dailyHabits = new HashMap<>();
        dailyHabits.put("score", calculateDailyHabits(quiz1, quiz2));
        dailyHabits.put("maxScore", DAILY_HABITS_WEIGHT);
        dailyHabits.put("sleepSchedule", Map.of(
                "match", quiz1.getSleepSchedule().equals(quiz2.getSleepSchedule()),
                "user1", quiz1.getSleepSchedule(),
                "user2", quiz2.getSleepSchedule()
        ));
        int cleanDiff = Math.abs(quiz1.getCleanlinessLevel() - quiz2.getCleanlinessLevel());
        dailyHabits.put("cleanliness", Map.of(
                "match", cleanDiff <= 1,
                "user1", quiz1.getCleanlinessLevel(),
                "user2", quiz2.getCleanlinessLevel(),
                "difference", cleanDiff
        ));
        dailyHabits.put("noiseTolerance", Map.of(
                "match", quiz1.getNoiseTolerance().equals(quiz2.getNoiseTolerance()),
                "user1", quiz1.getNoiseTolerance(),
                "user2", quiz2.getNoiseTolerance()
        ));
        breakdown.put("dailyHabits", dailyHabits);

        // Lifestyle
        Map<String, Object> lifestyle = new HashMap<>();
        lifestyle.put("score", calculateLifestyle(quiz1, quiz2));
        lifestyle.put("maxScore", LIFESTYLE_WEIGHT);
        lifestyle.put("socialLevel", Map.of(
                "match", quiz1.getSocialLevel().equals(quiz2.getSocialLevel()),
                "user1", quiz1.getSocialLevel(),
                "user2", quiz2.getSocialLevel()
        ));
        lifestyle.put("guestsFrequency", Map.of(
                "match", quiz1.getGuestsFrequency().equals(quiz2.getGuestsFrequency()),
                "user1", quiz1.getGuestsFrequency(),
                "user2", quiz2.getGuestsFrequency()
        ));
        lifestyle.put("cookingFrequency", Map.of(
                "match", quiz1.getCookingFrequency().equals(quiz2.getCookingFrequency()),
                "user1", quiz1.getCookingFrequency(),
                "user2", quiz2.getCookingFrequency()
        ));
        breakdown.put("lifestyle", lifestyle);

        return breakdown;
    }

    /**
     * Generate recommendation text based on score
     */
    public String getRecommendation(int score) {
        if (score >= 85) {
            return "Excellent match - proceed with confidence";
        } else if (score >= 70) {
            return "Good match - discuss minor differences";
        } else if (score >= 50) {
            return "Moderate match - have detailed conversation before committing";
        } else {
            return "Low compatibility - significant lifestyle differences";
        }
    }

    /**
     * Generate summary text
     */
    public String getSummary(int score, LifestyleQuiz quiz1, LifestyleQuiz quiz2) {
        StringBuilder summary = new StringBuilder();

        if (score >= 85) {
            summary.append("Highly compatible (").append(score).append("%). ");
        } else if (score >= 70) {
            summary.append("Good compatibility (").append(score).append("%). ");
        } else if (score >= 50) {
            summary.append("Moderate compatibility (").append(score).append("%). ");
        } else {
            summary.append("Low compatibility (").append(score).append("%). ");
        }

        // Add specific insights
        if (!quiz1.getSmoking().equals(quiz2.getSmoking())) {
            summary.append("Smoking preference mismatch. ");
        }
        if (!quiz1.getPets().equals(quiz2.getPets())) {
            summary.append("Pet preference mismatch. ");
        }
        if (quiz1.getSleepSchedule().equals(quiz2.getSleepSchedule())) {
            summary.append("Both have matching sleep schedules. ");
        }

        int cleanDiff = Math.abs(quiz1.getCleanlinessLevel() - quiz2.getCleanlinessLevel());
        if (cleanDiff <= 1) {
            summary.append("Similar cleanliness standards. ");
        } else if (cleanDiff > 3) {
            summary.append("Significant cleanliness difference. ");
        }

        return summary.toString().trim();
    }

    // Private helper methods

    private int calculateDealbreakers(LifestyleQuiz quiz1, LifestyleQuiz quiz2) {
        int score = 0;

        // Smoking (10 points)
        if (quiz1.getSmoking().equals(quiz2.getSmoking())) {
            score += 10;
        }

        // Pets (10 points)
        if (quiz1.getPets().equals(quiz2.getPets())) {
            score += 10;
        }

        // Drinking (10 points)
        if (quiz1.getDrinking().equals(quiz2.getDrinking())) {
            score += 10;
        }

        return score;
    }

    private int calculateDailyHabits(LifestyleQuiz quiz1, LifestyleQuiz quiz2) {
        int score = 0;

        // Sleep schedule (15 points)
        if (quiz1.getSleepSchedule().equals(quiz2.getSleepSchedule())) {
            score += 15;
        }

        // Cleanliness (15 points)
        int cleanDiff = Math.abs(quiz1.getCleanlinessLevel() - quiz2.getCleanlinessLevel());
        if (cleanDiff == 0) {
            score += 15;
        } else if (cleanDiff == 1) {
            score += 12;
        } else if (cleanDiff == 2) {
            score += 9;
        } else if (cleanDiff == 3) {
            score += 6;
        }
        // > 3 difference = 0 points

        // Noise tolerance (10 points)
        if (quiz1.getNoiseTolerance().equals(quiz2.getNoiseTolerance())) {
            score += 10;
        } else if (quiz1.getNoiseTolerance().name().equals("MODERATE") ||
                quiz2.getNoiseTolerance().name().equals("MODERATE")) {
            score += 5; // Moderate can adapt
        }

        return score;
    }

    private int calculateLifestyle(LifestyleQuiz quiz1, LifestyleQuiz quiz2) {
        int score = 0;

        // Social level (10 points)
        if (quiz1.getSocialLevel().equals(quiz2.getSocialLevel())) {
            score += 10;
        } else if (quiz1.getSocialLevel().name().equals("BALANCED") ||
                quiz2.getSocialLevel().name().equals("BALANCED")) {
            score += 5; // Balanced can adapt
        }

        // Guest frequency (10 points)
        if (quiz1.getGuestsFrequency().equals(quiz2.getGuestsFrequency())) {
            score += 10;
        } else {
            int diff = Math.abs(frequencyToNumber(quiz1.getGuestsFrequency()) -
                    frequencyToNumber(quiz2.getGuestsFrequency()));
            if (diff == 1) {
                score += 5; // Adjacent levels
            }
        }

        // Cooking frequency (10 points)
        if (quiz1.getCookingFrequency().equals(quiz2.getCookingFrequency())) {
            score += 10;
        } else {
            score += 5; // Partial points for mismatch
        }

        return score;
    }

    private int frequencyToNumber(com.roommatefinder.model.enums.Frequency frequency) {
        switch (frequency) {
            case RARELY: return 1;
            case SOMETIMES: return 2;
            case OFTEN: return 3;
            default: return 0;
        }
    }
}