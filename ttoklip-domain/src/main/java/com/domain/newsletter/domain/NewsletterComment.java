package com.domain.newsletter.domain;

import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentCreate;
import com.domain.member.domain.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "Newsletter")
public class NewsletterComment extends Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newsletter_id")
    private Newsletter newsletter;

    @Builder(access = AccessLevel.PRIVATE)
    public NewsletterComment(String content, Comment parent, Newsletter newsletter, Member member) {
        super(content, parent, member);
        this.newsletter = newsletter;
    }

    public static NewsletterComment withParentOf(final CommentCreate create, final Comment parent,
                                                 final Newsletter newsletter, final Member member) {
        return NewsletterComment.builder()
                .content(create.comment())
                .parent(parent)
                .newsletter(newsletter)
                .member(member)
                .build();
    }

    public static NewsletterComment orphanageOf(final CommentCreate create, final Newsletter newsletter, final Member member) {
        return NewsletterComment.builder()
                .content(create.comment())
                .parent(null)
                .newsletter(newsletter)
                .member(member)
                .build();
    }
}
