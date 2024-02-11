package com.api.ttoklip.domain.newsletter.comment.domain;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    public NewsletterComment(String content, Comment parent, Newsletter newsletter, Member member) {
        super(content, parent, member);
        this.newsletter = newsletter;
    }

    public static NewsletterComment withParentOf(final CommentCreateRequest request, final Comment parent,
                                                 final Newsletter newsletter) {
        return NewsletterComment.builder()
                .content(request.getComment())
                .parent(parent)
                .newsletter(newsletter)
                .build();
    }

    public static NewsletterComment orphanageOf(final CommentCreateRequest request, final Newsletter newsletter) {
        return NewsletterComment.builder()
                .content(request.getComment())
                .parent(null)
                .newsletter(newsletter)
                .build();
    }
}
