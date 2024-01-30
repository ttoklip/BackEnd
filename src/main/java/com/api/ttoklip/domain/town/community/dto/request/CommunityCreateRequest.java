package com.api.ttoklip.domain.town.community.dto.request;

import com.api.ttoklip.domain.town.community.Community;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCreateRequest {

    @NotEmpty
    @Size(max = 50)
    public String title;

    @NotEmpty
    @Size(max = 500)
    public String content;

    public List<MultipartFile> images;

    public Community toEntity() {
        return Community.builder()
                .title(title)
                .content(content)
                .build();
    }
}
