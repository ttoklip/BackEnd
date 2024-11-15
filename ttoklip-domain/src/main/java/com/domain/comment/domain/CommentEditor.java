package com.domain.comment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class CommentEditor {

    private final String content;

    public static CommentEditorBuilder builder() {
        return new CommentEditorBuilder();
    }

    public static class CommentEditorBuilder {
        private String content;

        CommentEditorBuilder() {
        }

        public CommentEditorBuilder comment(final String content) {
            if (StringUtils.hasText(content)) {
                this.content = content;
            }
            return this;
        }

        public CommentEditor build() {
            return new CommentEditor(content);
        }
    }
}
