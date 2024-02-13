package com.api.ttoklip.domain.town.community.post.entity;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.common.report.domain.Report;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.image.entity.CommunityImage;
import com.api.ttoklip.domain.town.community.like.entity.CommunityLike;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunityCreateRequest;
import com.api.ttoklip.domain.town.community.post.editor.CommunityPostEditor;
import com.api.ttoklip.domain.town.community.scrap.entity.CommunityScrap;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommunityImage> communityImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommunityComment> communityComments = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityLike> communityLikes = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityScrap> communityScraps = new ArrayList<>();

    public static Community from(final CommunityCreateRequest request) {
        return Community.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .member(getCurrentMember())
                .build();
    }

    @Builder
    public Community(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public CommunityPostEditor.CommunityPostEditorBuilder toEditor() {
        return CommunityPostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(final CommunityPostEditor editor) {
        this.title = editor.getTitle();
        this.content = editor.getContent();
    }

    public long getLikesCount() {
        return communityLikes.size();
    }

    public long getScrapsCount() {
        return communityScraps.size();
    }
}
