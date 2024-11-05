package com.api.ttoklip.domain.town.community.domain;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "Community")
public class CommunityComment extends Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @Builder
    private CommunityComment(String content, Comment parent, Community community, Member member) {
        super(content, parent, member); // Comment 클래스의 생성자 호출
        this.community = community;
    }

    public static CommunityComment withParentOf(final CommentCreateRequest request, final Comment parent,
                                                final Community community, final Member member) {
        return CommunityComment.builder()
                .content(request.getComment())
                .parent(parent)
                .community(community)
                .member(member)
                .build();
    }

    public static CommunityComment orphanageOf(final CommentCreateRequest request, final Community community,
                                               final Member member) {
        return CommunityComment.builder()
                .content(request.getComment())
                .parent(null)
                .community(community)
                .member(member)
                .build();
    }
}

