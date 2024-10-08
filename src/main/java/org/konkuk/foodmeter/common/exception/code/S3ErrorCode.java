package org.konkuk.foodmeter.common.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum S3ErrorCode implements DefaultErrorCode {
    // 400 BAD_REQUEST
    INVALID_IMAGE_EXTENSION(HttpStatus.BAD_REQUEST, 40030, "이미지 확장자는 jpg, png, webp만 가능합니다."),
    EMPTY_FILE_EXCEPTION(HttpStatus.BAD_REQUEST, 40031, "빈 파일입니다."),
    // 404 NOT_FOUND
    NOT_FOUND_IMAGE(HttpStatus.NOT_FOUND, 40431, "이미지를 찾을 수 없습니다."),
    // 500 INTERNAL_SERVER_ERROR
    FILE_CONVERT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, 50030, "파일 변환에 실패했습니다."),
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, 50031, "파일 업로드에 실패했습니다."),
    ;

    private HttpStatus httpStatus;
    private int code;
    private String message;
}
