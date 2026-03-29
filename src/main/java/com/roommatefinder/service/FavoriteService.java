package com.roommatefinder.service;

import com.roommatefinder.dto.request.FavoriteRequest;
import com.roommatefinder.exception.ConflictException;
import com.roommatefinder.exception.ResourceNotFoundException;
import com.roommatefinder.model.Favorite;
import com.roommatefinder.model.User;
import com.roommatefinder.repository.FavoriteRepository;
import com.roommatefinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    @Transactional
    public Favorite addFavorite(FavoriteRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if already favorited
        if (favoriteRepository.existsByUserAndListingIdAndListingType(
                user, request.getListingId(), request.getListingType())) {
            throw new ConflictException("Listing already in favorites");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setListingId(request.getListingId());
        favorite.setListingType(request.getListingType());

        return favoriteRepository.save(favorite);
    }

    public List<Favorite> getUserFavorites(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return favoriteRepository.findByUserOrderByCreatedAtDesc(user);
    }

    @Transactional
    public void removeFavorite(Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite not found"));

        favoriteRepository.delete(favorite);
    }
}