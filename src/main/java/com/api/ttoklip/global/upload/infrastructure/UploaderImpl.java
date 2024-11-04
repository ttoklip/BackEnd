package com.api.ttoklip.global.upload.infrastructure;

import com.api.ttoklip.global.upload.Uploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class UploaderImpl implements Uploader {

    private final S3FileUploader s3FileUploader;

    @Override
    public String uploadMultipartFile(final MultipartFile file) {
        return s3FileUploader.uploadMultipartFile(file);
    }

    @Override
    public List<String> uploadMultipartFiles(final List<MultipartFile> files) {
        return s3FileUploader.uploadMultipartFiles(files);
    }
}
