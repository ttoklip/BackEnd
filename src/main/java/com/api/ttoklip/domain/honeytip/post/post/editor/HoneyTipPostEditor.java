package com.api.ttoklip.domain.honeytip.post.post.editor;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.post.image.domain.HoneyTipImage;
import com.api.ttoklip.domain.honeytip.post.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.url.domain.HoneyTipUrl;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class HoneyTipPostEditor {
    private final String title;
    private final String content;
    private final Category category;
    private final List<HoneyTipImage> honeyTipImageList;
    private final List<HoneyTipUrl> honeyTipUrlList;

    // Private constructor to enforce the usage of the Builder.
    private HoneyTipPostEditor(String title, String content, Category category, List<HoneyTipImage> honeyTipImageList,
                               List<HoneyTipUrl> honeyTipUrlList) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.honeyTipImageList = honeyTipImageList;
        this.honeyTipUrlList = honeyTipUrlList;
    }

    public static HoneyTipPostEditorBuilder builder() {
        return new HoneyTipPostEditorBuilder();
    }

    public void applyTo(HoneyTip honeytip) {
        List<HoneyTipImage> updatedImages = honeyTipImageList != null ? honeyTipImageList : new ArrayList<>();
        List<HoneyTipUrl> updatedUrls = honeyTipUrlList != null ? honeyTipUrlList : new ArrayList<>();

        // 꿀팁 업데이트
        honeytip.updateHoneyTip(title, content, category, updatedImages, updatedUrls);

        // 이미지 및 URL 리스트와 연관 관계 설정
        updatedImages.forEach(image -> image.updateHoneyTip(honeytip));
        updatedUrls.forEach(url -> url.updateHoneyTip(honeytip));
    }

    public static class HoneyTipPostEditorBuilder {
        private String title;
        private String content;
        private Category category;
        private List<HoneyTipImage> honeyTipImageList;
        private List<HoneyTipUrl> honeyTipUrlList;

        HoneyTipPostEditorBuilder() {
        }

        public HoneyTipPostEditorBuilder title(final String title) {
            if (StringUtils.hasText(title)) {
                this.title = title;
            }
            return this;
        }

        public HoneyTipPostEditorBuilder content(final String content) {
            if (StringUtils.hasText(content)) {
                this.content = content;
            }
            return this;
        }

        public HoneyTipPostEditorBuilder category(final Category category) {
            this.category = category;
            return this;
        }

        public HoneyTipPostEditorBuilder honeyTipImageList(final List<HoneyTipImage> honeyTipImageList) {
            this.honeyTipImageList = honeyTipImageList;
            return this;
        }

        public HoneyTipPostEditorBuilder honeyTipUrlList(final List<HoneyTipUrl> honeyTipUrlList) {
            this.honeyTipUrlList = honeyTipUrlList;
            return this;
        }

        public HoneyTipPostEditor build() {
            return new HoneyTipPostEditor(title, content, category, honeyTipImageList, honeyTipUrlList);
        }
    }


}
