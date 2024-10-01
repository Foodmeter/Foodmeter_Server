package org.konkuk.foodmeter.common.exception.code;

import org.springframework.http.HttpStatus;

public interface DefaultErrorCode {
    HttpStatus getHttpStatus();

    int getCode();

    String getMessage();
}
