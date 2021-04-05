package com.hse.flowerapp.repository;

import com.hse.flowerapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getById(Long id);

    User findByEmail(String name);
}
