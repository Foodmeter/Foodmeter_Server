package org.konkuk.foodmeter.domain.user.repository;

import org.konkuk.foodmeter.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
