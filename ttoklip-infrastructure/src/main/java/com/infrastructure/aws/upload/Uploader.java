package com.infrastructure.aws.upload;

import java.util.List;

public interface Uploader {
    String uploadFile(FileInput file);

    List<String> uploadFiles(List<FileInput> files);
}