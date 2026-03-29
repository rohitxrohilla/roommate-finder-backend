package com.roommatefinder.model;

import com.roommatefinder.model.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "lifestyle_quiz")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LifestyleQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SleepSchedule sleepSchedule;

    @Column(nullable = false)
    private Integer cleanlinessLevel; // 1-10

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NoiseTolerance noiseTolerance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Frequency guestsFrequency;

    @Column(nullable = false)
    private Boolean smoking;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DrinkingHabit drinking;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PetPreference pets;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Frequency cookingFrequency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SocialLevel socialLevel;

    @Column(nullable = false)
    private Integer budgetMin;

    @Column(nullable = false)
    private Integer budgetMax;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}