package com.api.ttoklip.domain.honeytip.domain;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.member.domain.Member;
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

    @Builder(access = AccessLevel.PRIVATE)
    private HoneyTipComment(String content, Comment parent, HoneyTip honeyTip, Member member) {
        super(content, parent, member); // Comment 클래스의 생성자 호출
        this.honeyTip = honeyTip;
    }

    public static HoneyTipComment withParentOf(final CommentCreateRequest request, final Comment parent,
                                               final HoneyTip honeyTip, final Member member) {
        return HoneyTipComment.builder()
                .content(request.getComment())
                .parent(parent)
                .honeyTip(honeyTip)
                .member(member)
                .build();
    }

    public static HoneyTipComment orphanageOf(final CommentCreateRequest request, final HoneyTip honeyTip,
                                              final Member member) {
        return HoneyTipComment.builder()
                .content(request.getComment())
                .parent(null)
                .honeyTip(honeyTip)
                .member(member)
                .build();
    }
}
