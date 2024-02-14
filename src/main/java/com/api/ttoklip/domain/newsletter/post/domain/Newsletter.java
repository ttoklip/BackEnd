package com.api.ttoklip.domain.newsletter.post.domain;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.image.domain.NewsletterImage;
import com.api.ttoklip.domain.newsletter.post.dto.request.NewsletterCreateReq;
import com.api.ttoklip.domain.newsletter.scarp.entity.NewsletterScrap;
import com.api.ttoklip.domain.newsletter.url.domain.NewsletterUrl;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
public class Newsletter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    private String mainImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterImage> newsletterImageList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterUrl> newsletterUrlList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterComment> newsletterComments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterScrap> newsletterScraps = new ArrayList<>();

    public static Newsletter from(final NewsletterCreateReq req, final String mainImageUrl) {
        return Newsletter.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .category(req.getCategory())
                .mainImageUrl(mainImageUrl)
                .member(getCurrentMember())
                .build();
    }

    public long getScrapsCount() {
        return newsletterScraps.size();
    }
}
