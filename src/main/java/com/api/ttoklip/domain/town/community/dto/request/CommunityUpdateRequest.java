package com.api.ttoklip.domain.town.community.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class CommunityUpdateRequest {

    @NotEmpty
    @Size(max = 50)
    public String title;

    @NotEmpty
    @Size(max = 500)
    public String content;

    public List<MultipartFile> images;
}
