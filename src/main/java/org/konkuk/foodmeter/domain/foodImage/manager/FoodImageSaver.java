package org.konkuk.foodmeter.domain.foodImage.manager;

import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.domain.foodImage.FoodImage;
import org.konkuk.foodmeter.domain.foodImage.repository.FoodImageRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodImageSaver {
    private final FoodImageRepository foodImageRepository;

    public void save(final FoodImage foodImage) {
        foodImageRepository.save(foodImage);
    }
}
