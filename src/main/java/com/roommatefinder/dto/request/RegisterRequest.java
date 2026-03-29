package com.roommatefinder.dto.request;

import com.roommatefinder.model.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100)
    private String fullName;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Must be at least 18 years old")
    @Max(value = 100, message = "Age must be less than 100")
    private Integer age;

    @NotNull(message = "Gender is required")
    private Gender gender;

    private String occupation;

    private String bio;
}