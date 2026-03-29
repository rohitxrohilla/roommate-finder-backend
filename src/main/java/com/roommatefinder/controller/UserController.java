package com.roommatefinder.controller;

import com.roommatefinder.dto.response.UserResponse;
import com.roommatefinder.model.User;
import com.roommatefinder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody User user) {
        UserResponse response = userService.updateUser(id, user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/photo")
    public ResponseEntity<UserResponse> updateProfilePhoto(
            @PathVariable Long id,
            @RequestParam String photoUrl) {
        UserResponse response = userService.updateProfilePhoto(id, photoUrl);
        return ResponseEntity.ok(response);
    }
}