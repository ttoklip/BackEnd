package com.api.ttoklip.domain.town.community.post.editor;

import com.api.ttoklip.domain.honeytip.post.editor.HoneyTipPostEditor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class CommunityPostEditor {

    private final String title;
    private final String content;

    public static CommunityPostEditor.CommunityPostEditorBuilder builder() {
        return new CommunityPostEditor.CommunityPostEditorBuilder();
    }

    public static class CommunityPostEditorBuilder {
        private String title;
        private String content;

        CommunityPostEditorBuilder() {
        }

        public CommunityPostEditor.CommunityPostEditorBuilder title(final String title) {
            if (StringUtils.hasText(title)) {
                this.title = title;
            }
            return this;
        }

        public CommunityPostEditor.CommunityPostEditorBuilder content(final String content) {
            if (StringUtils.hasText(content)) {
                this.content = content;
            }
            return this;
        }

        public CommunityPostEditor build() {
            return new CommunityPostEditor(title, content);
        }
    }
}
