package org.konkuk.foodmeter.api.foodImage.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.api.foodImage.dto.request.FoodImageCreateDto;
import org.konkuk.foodmeter.api.foodImage.service.FoodImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/foodImage")
@Tag(name = "FoodImage", description = "FoodImage API Document")
public class FoodImageController {

    private final FoodImageService foodImageService;

    @PostMapping("/create")
    public ResponseEntity<Void> createImage(@Valid @ModelAttribute FoodImageCreateDto foodImageCreateDto) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteImage(final Long userId) {

        return ResponseEntity.ok().build();
    }
}
