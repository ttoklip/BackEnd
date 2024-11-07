package com.api.ttoklip.domain.bulletin.domain;

import com.api.ttoklip.domain.bulletin.dto.request.NoticeCreateRequest;
import com.api.ttoklip.domain.bulletin.editor.NoticePostEditor;
import com.api.ttoklip.domain.bulletin.editor.NoticePostEditor.NoticePostEditorBuilder;
import com.api.ttoklip.domain.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;


    public static Notice of(final NoticeCreateRequest request) {
        return Notice.builder()
                .content(request.getContent())
                .title(request.getTitle())
                .build();

    }

    public NoticePostEditorBuilder toEditor() {
        return NoticePostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(final NoticePostEditor editor) {
        this.title = editor.getTitle();
        this.content = editor.getContent();
    }

    public void deactivate() {
        super.deactivate();// Notice 엔티티 비활성화
    }
}
