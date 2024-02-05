package com.api.ttoklip.domain.town.community.post.entity;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.common.report.domain.Report;
import com.api.ttoklip.domain.honeytip.post.editor.HoneyTipPostEditor;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.image.entity.CommunityImage;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunityCreateRequest;
import com.api.ttoklip.domain.town.community.post.editor.CommunityPostEditor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Builder
    public Community(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateCommunityPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Community of(final CommunityCreateRequest request) {
        return Community.builder()
                .content(request.getContent())
                .title(request.getTitle())
                .build();
    }

    @Builder.Default
    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommunityImage> communityImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommunityComment> communityComments = new ArrayList<>();

    public CommunityPostEditor.CommunityPostEditorBuilder toEditor() {
        return CommunityPostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(final CommunityPostEditor editor) {
        this.title = editor.getTitle();
        this.content = editor.getContent();
    }
}
