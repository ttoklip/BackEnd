package com.api.common.upload;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface Uploader {

    String uploadMultipartFile(MultipartFile multipartFile);

    List<String> uploadMultipartFiles(List<MultipartFile> multipartFiles);
}
