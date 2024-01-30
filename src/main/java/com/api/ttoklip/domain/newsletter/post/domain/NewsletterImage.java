package com.api.ttoklip.domain.newsletter.post.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Newsletter_Image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsletterImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newsletter_id", nullable = false)
    private Newsletter newsletter;

    public void updateNewsletter(Newsletter newsletter) {
        this.newsletter = newsletter;
    }

    public NewsletterImage(String url, Newsletter newsletter) {
        this.url = url;
        this.newsletter = newsletter;
    }
}
