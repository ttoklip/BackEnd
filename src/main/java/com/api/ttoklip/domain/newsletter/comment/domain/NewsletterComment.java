package com.api.ttoklip.domain.newsletter.comment.domain;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "Newsletter")
public class NewsletterComment extends Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newsletter_id")
    private Newsletter newsletter;

    @Builder
    public NewsletterComment(String content, Comment parent, Newsletter newsletter) {
        super(content, parent);
        this.newsletter = newsletter;
    }
}
