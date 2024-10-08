package org.konkuk.foodmeter.api.foodImage.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.konkuk.foodmeter.api.foodImage.dto.request.FoodImageCreateDto;
import org.konkuk.foodmeter.api.foodImage.dto.response.FoodImageAddDto;
import org.konkuk.foodmeter.common.exception.S3Exception;
import org.konkuk.foodmeter.domain.foodImage.FoodImage;
import org.konkuk.foodmeter.domain.foodImage.Grade;
import org.konkuk.foodmeter.domain.foodImage.manager.FoodImageSaver;
import org.konkuk.foodmeter.domain.user.User;
import org.konkuk.foodmeter.domain.user.manager.UserRetriever;
import org.konkuk.foodmeter.external.service.s3.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodImageService {
    private final UserRetriever userRetriever;
    private final FoodImageSaver foodImageSaver;
    private final S3Service s3Service;
    private final EntityManager entityManager;

    @Transactional
    public FoodImageAddDto createFoodImage(final FoodImageCreateDto foodImageCreateDto, final Long userId) {
        User user = userRetriever.findById(userId);
        String imageUrl = null;
        try {
            imageUrl = s3Service.upload(foodImageCreateDto.foodImage(), user.getName());
        } catch (IOException ex) {
            log.info("Service Layer Upload Fail");
        }
        // AI model에서 응답을 받고 진행
        // Grage 설정 수정 필요
        FoodImage foodImage = FoodImage.builder().grade(Grade.A).user(user).imageUrl(imageUrl).build();
        foodImageSaver.save(foodImage);

        return FoodImageAddDto.from(foodImage);
    }

    @Transactional
    public void deleteImage(final String url) {
//        User user = userRetriever.findById(userId);
//        if (user.getProfileImg() == null) {
//            throw new S3Exception(S3ErrorCode.NOT_FOUND_IMAGE);
//        }

        try {
            s3Service.deleteImage(url);
        } catch (S3Exception e) {
            throw new S3Exception(e.getErrorCode());
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
