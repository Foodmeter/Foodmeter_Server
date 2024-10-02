package org.konkuk.foodmeter.api.foodImage.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record FoodImageCreateDto(
        @NotNull
        MultipartFile foodImage
) {
}
