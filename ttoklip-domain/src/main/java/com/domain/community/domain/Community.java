package com.domain.community.domain;

import com.domain.common.base.BaseEntity;
import com.domain.report.domain.Report;
import com.domain.community.domain.CommunityEditor.CommunityEditorBuilder;
import com.domain.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Community extends BaseEntity {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
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

    @Builder.Default
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityLike> communityLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityScrap> communityScraps = new ArrayList<>();

    public static Community from(final CommunityCreate create) {
        return Community.builder()
                .title(create.title())
                .content(create.content())
                .member(create.member())
                .build();
    }

    @Builder
    public Community(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public CommunityEditorBuilder toEditor() {
        return CommunityEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(final CommunityEditor editor) {
        this.title = editor.getTitle();
        this.content = editor.getContent();
    }

    @Override
    public void deactivate() {
        super.deactivate();
        deactivateCommunityImages();
        deactivateCommunityComments();
        deactivateReports();
        // 좋아요와 스크랩은 Hard Delete
        hardDeleteLikes();
        hardDeleteScraps();
    }

    private void deactivateCommunityImages() {
        communityImages.forEach(BaseEntity::deactivate);
    }

    private void deactivateCommunityComments() {
        communityComments.forEach(BaseEntity::deactivate);
    }

    private void deactivateReports() {
        reports.forEach(BaseEntity::deactivate);
    }

    private void hardDeleteLikes() {
        communityLikes.clear();
    }

    private void hardDeleteScraps() {
        communityScraps.clear();
    }
}
