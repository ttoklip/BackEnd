package com.api.ttoklip.domain.honeytip.editor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class HoneyTipPostEditor {
    private final String title;
    private final String content;

    public static HoneyTipPostEditorBuilder builder() {
        return new HoneyTipPostEditorBuilder();
    }

    public static class HoneyTipPostEditorBuilder {
        private String title;
        private String content;

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

        public HoneyTipPostEditor build() {
            return new HoneyTipPostEditor(title, content);
        }
    }

}
