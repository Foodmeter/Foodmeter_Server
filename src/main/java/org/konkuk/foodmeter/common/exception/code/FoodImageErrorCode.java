package org.konkuk.foodmeter.common.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FoodImageErrorCode implements DefaultErrorCode {
    // 404 NOT_FOUND
    NOT_FOUND_FOOD_IMAGE(HttpStatus.NOT_FOUND, 40420, "음식 이미지를 찾을 수 없습니다."),
    ;

    private HttpStatus httpStatus;
    private int code;
    private String message;
}
