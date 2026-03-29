package com.roommatefinder.service;

import com.roommatefinder.dto.response.UserResponse;
import com.roommatefinder.exception.ResourceNotFoundException;
import com.roommatefinder.model.User;
import com.roommatefinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return mapToResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Long userId, User updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Update fields
        if (updatedUser.getFullName() != null) {
            user.setFullName(updatedUser.getFullName());
        }
        if (updatedUser.getPhone() != null) {
            user.setPhone(updatedUser.getPhone());
        }
        if (updatedUser.getAge() != null) {
            user.setAge(updatedUser.getAge());
        }
        if (updatedUser.getGender() != null) {
            user.setGender(updatedUser.getGender());
        }
        if (updatedUser.getOccupation() != null) {
            user.setOccupation(updatedUser.getOccupation());
        }
        if (updatedUser.getBio() != null) {
            user.setBio(updatedUser.getBio());
        }

        User saved = userRepository.save(user);
        return mapToResponse(saved);
    }

    @Transactional
    public UserResponse updateProfilePhoto(Long userId, String photoUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        user.setProfilePhotoUrl(photoUrl);
        User saved = userRepository.save(user);

        return mapToResponse(saved);
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getEmail(),
                user.getFullName(),
                user.getPhone(),
                user.getAge(),
                user.getGender(),
                user.getOccupation(),
                user.getBio(),
                user.getProfilePhotoUrl(),
                user.getIsVerified()
        );
    }
}