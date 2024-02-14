package com.api.ttoklip.domain.newsletter.like.entity;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.*;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewsletterLike extends Serializers.Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newsletter_id")
    private Newsletter newsletter;

    public static NewsletterLike from(final Newsletter newsletter) {
        return NewsletterLike.builder()
                .member(getCurrentMember())
                .newsletter(newsletter)
                .build();
    }

}
