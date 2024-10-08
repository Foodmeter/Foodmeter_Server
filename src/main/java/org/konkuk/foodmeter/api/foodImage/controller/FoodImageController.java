package org.konkuk.foodmeter.api.foodImage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.api.foodImage.dto.request.FoodImageCreateDto;
import org.konkuk.foodmeter.api.foodImage.dto.response.FoodImageAddDto;
import org.konkuk.foodmeter.api.foodImage.service.FoodImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/foodImage")
@Tag(name = "FoodImage", description = "FoodImage API Document")
public class FoodImageController {

    private final FoodImageService foodImageService;

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "음식 사진 생성 API", description = "음식 사진을 S3에 생성합니다.")
    public ResponseEntity<FoodImageAddDto> createImage(@Valid @ModelAttribute final FoodImageCreateDto foodImageCreateDto, @RequestParam("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodImageService.createFoodImage(foodImageCreateDto, userId));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "음식 사진 삭제 API", description = "음식 사진을 S3에서 삭제합니다.")
    public ResponseEntity<Void> deleteImage(@RequestParam("image_url") final String url) {
        foodImageService.deleteImage(url);
        return ResponseEntity.ok().build();
    }
}
