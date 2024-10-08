package org.konkuk.foodmeter.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.common.exception.code.S3ErrorCode;

@Getter
@RequiredArgsConstructor
public class S3Exception extends RuntimeException {
    private final S3ErrorCode errorCode;
}
