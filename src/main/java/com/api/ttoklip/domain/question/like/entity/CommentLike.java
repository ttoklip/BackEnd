package com.api.ttoklip.domain.question.like.entity;

import com.api.ttoklip.domain.common.base.BaseTimeEntity;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.question.post.domain.Question;
import jakarta.persistence.*;
import lombok.*;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public static CommentLike from(final Question question) {
        return CommentLike.builder()
                .member(getCurrentMember())
                .question(question)
                .build();
    }

}
