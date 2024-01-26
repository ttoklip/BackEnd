package com.api.ttoklip.global.s3;


import static com.api.ttoklip.global.exception.ErrorType.EXCEEDING_FILE_COUNT;
import static com.api.ttoklip.global.exception.ErrorType.S3_CONNECT;
import static com.api.ttoklip.global.exception.ErrorType.S3_CONVERT;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3FileUploader {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dirname}")
    private String dirName;

    public List<String> uploadMultipartFiles(final List<MultipartFile> multipartFiles) {
        validInput(multipartFiles);
        return multipartFiles.stream()
                .map(this::uploadSingleFile)
                .toList();
    }

    private void validInput(final List<MultipartFile> multipartFiles) {
        validFileSize(multipartFiles);
        validFileNumber(multipartFiles.size());
    }

    private void validFileSize(final List<MultipartFile> multipartFiles) {
        long maxFileSize = 10485760; // 10MB로 설정
        multipartFiles.forEach(multipartFile -> {
            if (multipartFile.getSize() > maxFileSize) {
                throw new ApiException(ErrorType.EXCEEDING_FILE_SIZE);
            }
        });
    }

    private void validFileNumber(final int size) {
        // ToDo 임시 값, 몇개로 정할지 프론트와 협의
        if (size > 10) {
            throw new ApiException(EXCEEDING_FILE_COUNT);
        }
    }

    private String uploadSingleFile(final MultipartFile multipartFile) {
        try {
            File uploadFile = convert(multipartFile);
            return upload(uploadFile, dirName);
        } catch (IOException e) {
            throw new ApiException(S3_CONNECT);
        }
    }

    private File convert(final MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        originalFilename = validFileName(originalFilename);

        File convertFile = new File(originalFilename);
        validGenerateLocalFile(convertFile);

        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        }
        return convertFile;
    }

    private String validFileName(final String originalFilename) {
        if (!StringUtils.hasText(originalFilename)) {
            return UUID.randomUUID().toString();
        }
        return originalFilename;
    }

    private void validGenerateLocalFile(final File convertFile) throws IOException {
        if (!convertFile.createNewFile()) {
            throw new ApiException(S3_CONVERT);
        }
    }

    private String upload(final File uploadFile, final String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제
        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(final File uploadFile, final String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(final File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }
}
