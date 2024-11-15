package com.domain.honeytip.domain.request;

import com.domain.common.vo.Category;
import com.domain.common.vo.PostRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HoneyTipCreate implements PostRequest {

    @NotEmpty
    @Size(max = 500)
    public String title;

    @NotEmpty
    @Size(max = 5000)
    public String content;

    @NotNull
    public Category category;

    public static HoneyTipCreate of(String title, String content, Category category) {
        return new HoneyTipCreate(title, content, category);
    }
}
