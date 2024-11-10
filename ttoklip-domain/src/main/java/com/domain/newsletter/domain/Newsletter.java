package com.domain.newsletter.domain;

import com.domain.common.base.BaseEntity;
import com.domain.common.vo.Category;
import com.domain.member.domain.Member;
import com.domain.newsletter.application.request.NewsletterCreate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Newsletter extends BaseEntity {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
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
    private List<NewsletterImage> newsletterImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterUrl> newsletterUrlList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterComment> newsletterComments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterLike> newsletterLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterScrap> newsletterScraps = new ArrayList<>();

    public static Newsletter from(final NewsletterCreate create) {
        return Newsletter.builder()
                .title(create.getTitle())
                .content(create.getContent())
                .category(create.getCategory())
                .mainImageUrl(create.getMainImageUrl())
                .member(create.getMember())
                .build();
    }
}
