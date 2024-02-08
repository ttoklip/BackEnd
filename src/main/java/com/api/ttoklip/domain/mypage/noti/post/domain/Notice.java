package com.api.ttoklip.domain.mypage.noti.post.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.mypage.noti.post.dto.request.NoticeRequest;
import com.api.ttoklip.domain.mypage.noti.post.editor.NoticePostEditor;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    public static Notice of (final NoticeRequest request){
        return Notice.builder()
                .content(request.getContent())
                .title(request.getTitle())
                .build();

    }

    public NoticePostEditor.NoticePostEditorBuilder toEditor() {
        return NoticePostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(final NoticePostEditor editor) {
        this.title = editor.getTitle();
        this.content = editor.getContent();
    }
}
