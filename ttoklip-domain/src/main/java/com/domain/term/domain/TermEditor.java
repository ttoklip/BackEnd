package com.domain.term.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class TermEditor {
    private final String title;
    private final String content;

    public static TermEditorBuilder builder() {
        return new TermEditorBuilder();
    }

    public static class TermEditorBuilder {
        private String title;
        private String content;

        TermEditorBuilder() {
        }

        public TermEditorBuilder title(final String title) {
            if (StringUtils.hasText(title)) {
                this.title = title;
            }
            return this;
        }

        public TermEditorBuilder content(final String content) {
            if (StringUtils.hasText(content)) {
                this.content = content;
            }
            return this;
        }

        public TermEditor build() {
            return new TermEditor(title, content);
        }
    }
}
