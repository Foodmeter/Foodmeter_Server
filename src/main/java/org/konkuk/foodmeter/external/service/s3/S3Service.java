package org.konkuk.foodmeter.external.service.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.konkuk.foodmeter.common.exception.S3Exception;
import org.konkuk.foodmeter.common.exception.code.S3ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("image/jpeg", "image/png", "image/jpg", "image/webp", "image/heic");
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {

        validateEmpty(multipartFile);
        validateExtension(multipartFile);

        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new S3Exception(S3ErrorCode.FILE_CONVERT_FAIL));

        return upload(uploadFile, dirName);
    }

    public void deleteImage(String imageUrl) throws IOException {
        // URL에서 객체 키 추출
        String key = extractKeyFromUrl(imageUrl);
        // 객체 삭제 요청 생성 및 실행
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
        log.info("S3 파일 삭제 성공: " + key);
    }

    public String extractKeyFromUrl(String imageUrl) throws IOException {
        URL s3Url = new URL(imageUrl);
        String path = s3Url.getPath();
        return path.substring(1);
    }

    private String upload(File uploadFile, String dirName) {

        final String extension = getFileExtension(uploadFile.getName());
        final String fileName = generateImageFileName(extension, dirName);
        String uploadImageUrl = putS3(uploadFile, fileName);

        deleteFile(uploadFile); // convert() 과정에서 로컬에 생성된 파일 삭제

        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {

        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile) // PublicRead 권한으로 upload
        );

        return amazonS3Client.getUrl(bucket, fileName).toString(); // File의 URL return
    }

    private void deleteFile(File targetFile) {

        String name = targetFile.getName();

        // convert() 과정에서 로컬에 생성된 파일을 삭제
        if (targetFile.delete()) {
            log.info(name + "파일 삭제 완료");
        } else {
            log.info(name + "파일 삭제 실패");
        }
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException {
        // 기존 파일 이름으로 새로운 File 객체 생성
        // 해당 객체는 프로그램이 실행되는 로컬 디렉토리(루트 디렉토리)에 위치하게 됨
        File convertFile = new File(multipartFile.getOriginalFilename());

        if (convertFile.createNewFile()) { // 해당 경로에 파일이 없을 경우, 새 파일 생성

            try (FileOutputStream fos = new FileOutputStream(convertFile)) {

                // multipartFile의 내용을 byte로 가져와서 write
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }

        // 새파일이 성공적으로 생성되지 않았다면, 비어있는 Optional 객체를 반환
        return Optional.empty();
    }

    private void validateEmpty(MultipartFile image) {
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new S3Exception(S3ErrorCode.EMPTY_FILE_EXCEPTION);
        }
    }

    private void validateExtension(MultipartFile image) {
        String contentType = image.getContentType();
        if (!IMAGE_EXTENSIONS.contains(contentType)) {
            throw new S3Exception(S3ErrorCode.INVALID_IMAGE_EXTENSION);
        }
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private String generateImageFileName(String extension, String dirName) {
        // 현재 날짜와 시간을 가져옵니다.
        LocalDateTime now = LocalDateTime.now();
        // 원하는 포맷 지정 (예: yyyyMMdd_HHmmss)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        // 포맷에 맞게 문자열 생성
        String formattedDateTime = now.format(formatter);
        // 파일명 생성 (예: {userName}/20241007_111552.jpg)
        return dirName + "/" + formattedDateTime + "." + extension;
    }
}
