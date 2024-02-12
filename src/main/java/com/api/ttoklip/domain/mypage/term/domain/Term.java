package com.api.ttoklip.domain.mypage.term.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.mypage.term.dto.request.TermCreateRequest;
import com.api.ttoklip.domain.mypage.term.editor.TermEditor;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Term extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    public static Term of(final TermCreateRequest request) {
        return Term.builder()
                .content(request.getContent())
                .title(request.getTitle())
                .build();

    }

    public TermEditor.TermEditorBuilder toEditor() {
        return TermEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(final TermEditor editor) {
        this.title = editor.getTitle();
        this.content = editor.getContent();
    }
}
