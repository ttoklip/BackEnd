package com.api.ttoklip.domain.honeytip.post.post.editor;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.post.post.domain.Honeytip;
import com.api.ttoklip.domain.honeytip.post.post.domain.HoneytipImage;
import com.api.ttoklip.domain.honeytip.post.post.domain.HoneytipUrl;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HoneytipPostEditor {
    private final String title;
    private final String content;
    private final Category category;
    private final List<HoneytipImage> honeytipImageList;
    private final List<HoneytipUrl> honeytipUrlList;

    // Private constructor to enforce the usage of the Builder.
    private HoneytipPostEditor(String title, String content, Category category, List<HoneytipImage> honeytipImageList, List<HoneytipUrl> honeytipUrlList) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.honeytipImageList = honeytipImageList;
        this.honeytipUrlList = honeytipUrlList;
    }

    public static HoneytipPostEditorBuilder builder() {
        return new HoneytipPostEditorBuilder();
    }

    public void applyTo(Honeytip honeytip) {
        List<HoneytipImage> updatedImages = honeytipImageList != null ? honeytipImageList : new ArrayList<>();
        List<HoneytipUrl> updatedUrls = honeytipUrlList != null ? honeytipUrlList : new ArrayList<>();

        // 꿀팁 업데이트
        honeytip.updateHoneytip(title, content, category, updatedImages, updatedUrls);

        // 이미지 및 URL 리스트와 연관 관계 설정
        updatedImages.forEach(image -> image.updateHoneytip(honeytip));
        updatedUrls.forEach(url -> url.updateHoneytip(honeytip));
    }

    public static class HoneytipPostEditorBuilder {
        private String title;
        private String content;
        private Category category;
        private List<HoneytipImage> honeytipImageList;
        private List<HoneytipUrl> honeytipUrlList;

        HoneytipPostEditorBuilder() {
        }

        public HoneytipPostEditorBuilder title(final String title) {
            if (StringUtils.hasText(title)) {
                this.title = title;
            }
            return this;
        }

        public HoneytipPostEditorBuilder content(final String content) {
            if (StringUtils.hasText(content)) {
                this.content = content;
            }
            return this;
        }

        public HoneytipPostEditorBuilder category(final Category category) {
            this.category = category;
            return this;
        }

        public HoneytipPostEditorBuilder honeytipImageList(final List<HoneytipImage> honeytipImageList) {
            this.honeytipImageList = honeytipImageList;
            return this;
        }

        public HoneytipPostEditorBuilder honeytipUrlList(final List<HoneytipUrl> honeytipUrlList) {
            this.honeytipUrlList = honeytipUrlList;
            return this;
        }

        public HoneytipPostEditor build() {
            return new HoneytipPostEditor(title, content, category, honeytipImageList, honeytipUrlList);
        }
    }


}
