package org.konkuk.foodmeter.api.foodImage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.konkuk.foodmeter.api.foodImage.dto.request.FoodImageCreateDto;
import org.konkuk.foodmeter.api.foodImage.dto.response.FoodImageAddDto;
import org.konkuk.foodmeter.common.exception.FoodImageException;
import org.konkuk.foodmeter.common.exception.S3Exception;
import org.konkuk.foodmeter.common.exception.code.FoodImageErrorCode;
import org.konkuk.foodmeter.domain.foodImage.FoodImage;
import org.konkuk.foodmeter.domain.foodImage.Grade;
import org.konkuk.foodmeter.domain.foodImage.manager.FoodImageRemover;
import org.konkuk.foodmeter.domain.foodImage.manager.FoodImageRetriever;
import org.konkuk.foodmeter.domain.foodImage.manager.FoodImageSaver;
import org.konkuk.foodmeter.domain.user.User;
import org.konkuk.foodmeter.domain.user.manager.UserRetriever;
import org.konkuk.foodmeter.external.service.s3.S3Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodImageService {
    private final UserRetriever userRetriever;
    private final FoodImageSaver foodImageSaver;
    private final FoodImageRemover foodImageRemover;
    private final FoodImageRetriever foodImageRetriever;
    private final S3Service s3Service;
    private final RestTemplate restTemplate; // RestTemplate 주입


    @Transactional
    public FoodImageAddDto createFoodImage(final FoodImageCreateDto foodImageCreateDto, final Long userId) {
        User user = userRetriever.findById(userId);
        String imageUrl = null;
        try {
            imageUrl = s3Service.upload(foodImageCreateDto.foodImage(), user.getName());
        } catch (IOException ex) {
            log.info("Service Layer Upload Fail");
        }
        log.info("url : " + imageUrl);
        // AI model에서 응답을 받고 진행
        String aiModelUrl = "http://ai-model:5101/analyze"; // AI 모델 API URL
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 JSON 데이터
        Map<String, String> requestBody = Map.of("imageUrl", imageUrl);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // AI 모델로부터 분석 결과 받기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                aiModelUrl, HttpMethod.POST, requestEntity, String.class);

        String responseBody = responseEntity.getBody();
        log.info("AI 모델 응답 (String): " + responseBody);

        // 응답을 Map으로 변환하여 grade 값을 추출
        String gradeDetail = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> responseMap = objectMapper.readValue(responseBody, Map.class);
            gradeDetail = responseMap.get("grade");
        } catch (Exception e) {
            log.error("AI 모델 응답 파싱 실패: ", e);
            throw new RuntimeException("AI 모델 응답 파싱 실패");
        }
        Grade grade = convertStringToGrade(gradeDetail);
        log.info("grade : " + grade);
        FoodImage foodImage = FoodImage.builder().grade(grade).user(user).imageUrl(imageUrl).build();
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
        foodImageRemover.deleteByImageUrl(url);
    }

    public List<String> getAllImages(final Long userId) {
        User user = userRetriever.findById(userId);
        List<FoodImage> foodImages = foodImageRetriever.findAllByUser(user);
        if (foodImages == null) {
            throw new FoodImageException(FoodImageErrorCode.NOT_FOUND_FOOD_IMAGE);
        } else {
            return foodImages.stream()
                    .map(FoodImage::getImageUrl)
                    .collect(Collectors.toList());
        }
    }

    private Grade convertStringToGrade(String gradeDetail){
        return switch (gradeDetail) {
            case "1++" -> Grade.A;
            case "1+" -> Grade.B;
            case "1" -> Grade.C;
            case "2" -> Grade.D;
            case "3" -> Grade.E;
            default -> throw new IllegalArgumentException("유효하지 않은 grade 값: " + gradeDetail);
        };
    }
}
