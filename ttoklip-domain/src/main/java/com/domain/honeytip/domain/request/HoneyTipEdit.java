package com.domain.honeytip.domain.request;

import com.domain.common.vo.PostRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HoneyTipEdit implements PostRequest {

    @NotEmpty
    @Size(max = 500)
    private String title;

    @NotEmpty
    @Size(max = 5000)
    private String content;

    public static HoneyTipEdit of(String title, String content) {
        return new HoneyTipEdit(title, content);
    }

}
