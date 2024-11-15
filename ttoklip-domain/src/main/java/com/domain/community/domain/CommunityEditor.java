package com.domain.community.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class CommunityEditor {

    private final String title;
    private final String content;

    public static CommunityEditorBuilder builder() {
        return new CommunityEditorBuilder();
    }

    public static class CommunityEditorBuilder {
        private String title;
        private String content;

        CommunityEditorBuilder() {
        }

        public CommunityEditorBuilder title(final String title) {
            if (StringUtils.hasText(title)) {
                this.title = title;
            }
            return this;
        }

        public CommunityEditorBuilder content(final String content) {
            if (StringUtils.hasText(content)) {
                this.content = content;
            }
            return this;
        }

        public CommunityEditor build() {
            return new CommunityEditor(title, content);
        }
    }
}
