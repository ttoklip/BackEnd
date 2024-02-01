package com.api.ttoklip.domain.newsletter.post.domain;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Newsletter")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Newsletter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "category")
    private Category category;

    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterImage> newsletterImageList = new ArrayList<>();

    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterUrl> newsletterUrlList = new ArrayList<>();

    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterComment> newsletterComments = new ArrayList<>();


    @Builder
    public Newsletter(String title, String content, Category category, List<NewsletterImage> newsletterImageList, List<NewsletterUrl> newsletterUrlList) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.newsletterImageList = newsletterImageList;
        this.newsletterUrlList = newsletterUrlList;
    }
}
