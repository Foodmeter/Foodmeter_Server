package org.konkuk.foodmeter.domain.foodImage.manager;

import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.common.exception.FoodImageException;
import org.konkuk.foodmeter.common.exception.code.FoodImageErrorCode;
import org.konkuk.foodmeter.domain.foodImage.FoodImage;
import org.konkuk.foodmeter.domain.foodImage.repository.FoodImageRepository;
import org.konkuk.foodmeter.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FoodImageRetriever {

    private final FoodImageRepository foodImageRepository;

    public FoodImage findById(Long id) {
        return foodImageRepository.findById(id)
                .orElseThrow(() -> new FoodImageException(FoodImageErrorCode.NOT_FOUND_FOOD_IMAGE));
    }

    public List<FoodImage> findAllByUser(User user) {
        Optional<List<FoodImage>> foodImages = foodImageRepository.findAllByUser(user);
        if (foodImages.isPresent()) {
            return foodImages.get();
        } else {
            return null;
        }
    }
}
