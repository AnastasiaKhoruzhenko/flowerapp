package com.hse.flowerapp.repository;

import com.hse.flowerapp.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Feedback getById(Long id);
}