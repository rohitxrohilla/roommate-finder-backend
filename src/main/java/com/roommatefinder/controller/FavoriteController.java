package com.roommatefinder.controller;

import com.roommatefinder.dto.request.FavoriteRequest;
import com.roommatefinder.dto.response.MessageResponse;
import com.roommatefinder.model.Favorite;
import com.roommatefinder.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@Valid @RequestBody FavoriteRequest request) {
        Favorite favorite = favoriteService.addFavorite(request);
        return new ResponseEntity<>(favorite, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Favorite>> getUserFavorites(@PathVariable Long userId) {
        List<Favorite> favorites = favoriteService.getUserFavorites(userId);
        return ResponseEntity.ok(favorites);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> removeFavorite(@PathVariable Long id) {
        favoriteService.removeFavorite(id);
        return ResponseEntity.ok(new MessageResponse("Removed from favorites"));
    }
}