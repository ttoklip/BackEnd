package com.domain.honeytip.domain;

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
@DiscriminatorValue(value = "HoneyTip")
public class HoneyTipComment extends Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "honey_tip_id")
    private HoneyTip honeyTip;

    @Builder
    private HoneyTipComment(String content, Comment parent, HoneyTip honeyTip, Member member) {
        super(content, parent, member); // Comment 클래스의 생성자 호출
        this.honeyTip = honeyTip;
    }

    public static HoneyTipComment withParentOf(final CommentCreate request, final Comment parent,
                                               final HoneyTip honeyTip, final Member member) {
        return HoneyTipComment.builder()
                .content(request.comment())
                .parent(parent)
                .honeyTip(honeyTip)
                .member(member)
                .build();
    }

    public static HoneyTipComment orphanageOf(final CommentCreate request, final HoneyTip honeyTip,
                                              final Member member) {
        return HoneyTipComment.builder()
                .content(request.comment())
                .parent(null)
                .honeyTip(honeyTip)
                .member(member)
                .build();
    }
}
