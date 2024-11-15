package com.domain.cart.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class CartPostEditor {

    private final String title;
    private final String content;

    public static CartPostEditorBuilder builder() {
        return new CartPostEditorBuilder();
    }

    public static class CartPostEditorBuilder {
        private String title;
        private String content;

        CartPostEditorBuilder() {
        }

        public CartPostEditorBuilder title(final String title) {
            if (StringUtils.hasText(title)) {
                this.title = title;
            }
            return this;
        }

        public CartPostEditorBuilder content(final String content) {
            if (StringUtils.hasText(content)) {
                this.content = content;
            }
            return this;
        }

        public CartPostEditor build() {
            return new CartPostEditor(title, content);
        }
    }
}


