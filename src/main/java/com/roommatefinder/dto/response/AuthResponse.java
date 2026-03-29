package com.roommatefinder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String email;
    private String fullName;
    private Boolean hasCompletedQuiz;
    private Boolean isVerified;
    private String message;
}