package com.api.ttoklip.domain.term.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.term.dto.request.TermCreateRequest;
import com.api.ttoklip.domain.term.editor.TermEditor;
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

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    public static Term from(final TermCreateRequest request) {
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
