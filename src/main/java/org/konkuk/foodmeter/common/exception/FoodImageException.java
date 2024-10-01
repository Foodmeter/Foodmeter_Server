package org.konkuk.foodmeter.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.common.exception.code.FoodImageErrorCode;

@Getter
@RequiredArgsConstructor
public class FoodImageException extends RuntimeException {
    private final FoodImageErrorCode errorCode;
}
