package org.konkuk.foodmeter.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.common.exception.code.UserErrorCode;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException {
    private final UserErrorCode errorCode;
}
