package org.konkuk.foodmeter.domain.foodImage.manager;

import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.domain.foodImage.repository.FoodImageRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodImageRemover {
    private final FoodImageRepository foodImageRepository;

    public void deleteById(Long foodImageId) {
        foodImageRepository.deleteById(foodImageId);
    }

    public void deleteByImageUrl(String imageUrl) {
        foodImageRepository.deleteByImageUrl(imageUrl);
    }
}
