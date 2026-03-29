package com.roommatefinder.repository;

import com.roommatefinder.model.LifestyleQuiz;
import com.roommatefinder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LifestyleQuizRepository extends JpaRepository<LifestyleQuiz, Long> {

    Optional<LifestyleQuiz> findByUser(User user);

    Optional<LifestyleQuiz> findByUser_UserId(Long userId);

    Boolean existsByUser(User user);
}