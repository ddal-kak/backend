package ddalkak.presigned_url.service;

import ddalkak.presigned_url.dto.FileMetaData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class PresignedURLGenerator {
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    private final S3Presigner s3Presigner;
    private static final String prefix = "images/";

    public String generateUploadUrl(FileMetaData fileMetaData) {
        log.info("baseName={}", fileMetaData.baseName());
        log.info("mime={}", fileMetaData.mime());
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(prefix + fileMetaData.baseName())
                .contentType(fileMetaData.mime())
                .build();
        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(builder -> builder
                .signatureDuration(Duration.ofHours(1))
                .putObjectRequest(request));
        return presignedRequest.url().toString();
    }
}
