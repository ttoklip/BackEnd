package com.api.ttoklip.domain.newsletter.post.domain;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Newsletter")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
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

    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL)
    private List<NewsletterImage> honeytipImageList = new ArrayList<>();

    @OneToMany(mappedBy = "newsletter", cascade = CascadeType.ALL)
    private List<NewsletterUrl> honeytipUrlList = new ArrayList<>();


    @Builder
    public Newsletter(String title, String content, Category category, List<NewsletterImage> honeytipImageList, List<NewsletterUrl> honeytipUrlList) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.honeytipImageList = honeytipImageList;
        this.honeytipUrlList = honeytipUrlList;
    }


}
