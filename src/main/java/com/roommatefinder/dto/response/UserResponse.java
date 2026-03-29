package com.roommatefinder.dto.response;

import com.roommatefinder.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String email;
    private String fullName;
    private String phone;
    private Integer age;
    private Gender gender;
    private String occupation;
    private String bio;
    private String profilePhotoUrl;
    private Boolean isVerified;
}