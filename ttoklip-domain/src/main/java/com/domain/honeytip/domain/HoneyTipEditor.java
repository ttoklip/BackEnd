package com.domain.honeytip.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class HoneyTipEditor {
    private final String title;
    private final String content;

    public static HoneyTipEditorBuilder builder() {
        return new HoneyTipEditorBuilder();
    }

    public static class HoneyTipEditorBuilder {
        private String title;
        private String content;

        HoneyTipEditorBuilder() {
        }

        public HoneyTipEditorBuilder title(final String title) {
            if (StringUtils.hasText(title)) {
                this.title = title;
            }
            return this;
        }

        public HoneyTipEditorBuilder content(final String content) {
            if (StringUtils.hasText(content)) {
                this.content = content;
            }
            return this;
        }

        public HoneyTipEditor build() {
            return new HoneyTipEditor(title, content);
        }
    }

}
