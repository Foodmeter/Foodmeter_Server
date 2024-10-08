package org.konkuk.foodmeter.api.foodImage.dto.response;

import org.konkuk.foodmeter.domain.foodImage.FoodImage;

public record FoodImageAddDto(
        String imageUrl
) {
    public static FoodImageAddDto from(FoodImage foodImage) {
        return new FoodImageAddDto(
                foodImage.getImageUrl()
        );
    }
}
