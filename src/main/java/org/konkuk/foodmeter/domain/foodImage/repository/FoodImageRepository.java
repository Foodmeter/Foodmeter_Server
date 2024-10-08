package org.konkuk.foodmeter.domain.foodImage.repository;

import org.konkuk.foodmeter.domain.foodImage.FoodImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodImageRepository extends JpaRepository<FoodImage, Long> {
    void deleteByImageUrl(String imageUrl);
}
