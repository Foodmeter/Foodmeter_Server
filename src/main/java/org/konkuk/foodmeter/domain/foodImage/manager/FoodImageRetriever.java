package org.konkuk.foodmeter.domain.foodImage.manager;

import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.common.exception.FoodImageException;
import org.konkuk.foodmeter.common.exception.code.FoodImageErrorCode;
import org.konkuk.foodmeter.domain.foodImage.FoodImage;
import org.konkuk.foodmeter.domain.foodImage.repository.FoodImageRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodImageRetriever {

    private final FoodImageRepository foodImageRepository;

    public FoodImage findById(Long id) {
        return foodImageRepository.findById(id)
                .orElseThrow(() -> new FoodImageException(FoodImageErrorCode.NOT_FOUND_FOOD_IMAGE));
    }
}
