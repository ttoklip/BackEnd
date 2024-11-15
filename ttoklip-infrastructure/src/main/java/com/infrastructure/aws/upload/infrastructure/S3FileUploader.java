package com.infrastructure.aws.upload.infrastructure;

import static com.common.exception.ErrorType.EXCEEDING_FILE_COUNT;
import static com.common.exception.ErrorType.S3_CONNECT;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.infrastructure.aws.upload.FileInput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3FileUploader {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dirname}")
    private String dirName;

    public List<String> uploadFiles(final List<FileInput> files) {
        validInput(files);
        return files.stream()
                .map(this::uploadSingleFile)
                .toList();
    }

    public String uploadFile(final FileInput file) {
        return uploadSingleFile(file);
    }

    private void validInput(final List<FileInput> files) {
        validFileSize(files);
        validFileNumber(files.size());
    }

    private void validFileSize(final List<FileInput> files) {
        long maxFileSize = 10485760; // 10MB로 설정
        files.forEach(file -> {
            if (file.getSize() > maxFileSize) {
                throw new ApiException(ErrorType.EXCEEDING_FILE_SIZE);
            }
        });
    }

    private void validFileNumber(final int size) {
        if (size > 10) {
            throw new ApiException(EXCEEDING_FILE_COUNT);
        }
    }

    private String uploadSingleFile(final FileInput file) {
        try {
            File uploadFile = convert(file);
            return upload(uploadFile, dirName);
        } catch (IOException e) {
            throw new ApiException(S3_CONNECT);
        }
    }

    private File convert(final FileInput file) throws IOException {
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
        String extension = getFileExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + extension;
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex);
        }
        return ""; // 확장자가 없는 경우 빈 문자열 반환
    }

    private void validGenerateLocalFile(final File convertFile) throws IOException {
        if (!convertFile.createNewFile()) {
            throw new ApiException(ErrorType.S3_CONVERT);
        }
    }

    private String upload(final File uploadFile, final String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + uploadFile.getName();
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
