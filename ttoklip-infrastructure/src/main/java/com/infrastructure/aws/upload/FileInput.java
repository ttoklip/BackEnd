package com.infrastructure.aws.upload;

import java.io.IOException;

public interface FileInput {
    String getOriginalFilename();

    byte[] getBytes() throws IOException;

    long getSize();
}