package com.api.common.upload;

import com.infrastructure.aws.upload.FileInput;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileAdapter implements FileInput {
    private final MultipartFile multipartFile;

    public MultipartFileAdapter(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public String getOriginalFilename() {
        return multipartFile.getOriginalFilename();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return multipartFile.getBytes();
    }

    @Override
    public long getSize() {
        return multipartFile.getSize();
    }
}

