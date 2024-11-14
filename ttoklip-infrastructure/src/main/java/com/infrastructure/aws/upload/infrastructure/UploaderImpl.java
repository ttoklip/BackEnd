package com.infrastructure.aws.upload.infrastructure;

import com.infrastructure.aws.upload.FileInput;
import com.infrastructure.aws.upload.Uploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UploaderImpl implements Uploader {

    private final S3FileUploader s3FileUploader;

    @Override
    public String uploadFile(final FileInput file) {
        return s3FileUploader.uploadFile(file);
    }

    @Override
    public List<String> uploadFiles(final List<FileInput> files) {
        return s3FileUploader.uploadFiles(files);
    }
}
