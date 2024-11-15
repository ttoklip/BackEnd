package com.domain.bulletin.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class NoticeEditor {

    private final String title;
    private final String content;

    public static NoticePostEditorBuilder builder() {
        return new NoticePostEditorBuilder();
    }

    public static class NoticePostEditorBuilder {
        private String title;
        private String content;

        NoticePostEditorBuilder() {
        }

        public NoticePostEditorBuilder title(final String title) {
            if (StringUtils.hasText(title)) {
                this.title = title;
            }
            return this;
        }

        public NoticePostEditorBuilder content(final String content) {
            if (StringUtils.hasText(content)) {
                this.content = content;
            }
            return this;
        }

        public NoticeEditor build() {
            return new NoticeEditor(title, content);
        }
    }

}
