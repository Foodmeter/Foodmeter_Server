package org.konkuk.foodmeter.domain.foodImage.repository;

import org.konkuk.foodmeter.domain.foodImage.FoodImage;
import org.konkuk.foodmeter.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodImageRepository extends JpaRepository<FoodImage, Long> {
    void deleteByImageUrl(String imageUrl);

    Optional<List<FoodImage>> findAllByUser(User user);
}
