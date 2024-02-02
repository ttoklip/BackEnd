package com.api.ttoklip.domain.town.community.comment;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "Community")
public class CommunityComment extends Comment {

    public static CommunityComment withParentOf(final CommentCreateRequest request, final Comment parent,
                                      final Community community) {
        return CommunityComment.builder()
                .content(request.getComment())
                .parent(parent)
                .community(community)
                .build();
    }

    public static CommunityComment orphanageOf(final CommentCreateRequest request, final Community community) {
        return CommunityComment.builder()
                .content(request.getComment())
                .parent(null)
                .community(community)
                .build();
    }
}

