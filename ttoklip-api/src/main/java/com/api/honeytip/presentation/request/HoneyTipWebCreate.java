package com.api.honeytip.presentation.request;

import com.domain.common.vo.Category;
import com.domain.common.vo.PostRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
public class HoneyTipWebCreate implements PostRequest {

    @NotEmpty
    @Size(max = 500)
    public String title;

    @NotEmpty
    @Size(max = 5000)
    public String content;

    @NotNull
    public String category;

    public List<MultipartFile> images;


    public Category getCategory() {
        // 문자열을 enum으로 변환
        return Category.findCategoryByValue(category);
    }
}
