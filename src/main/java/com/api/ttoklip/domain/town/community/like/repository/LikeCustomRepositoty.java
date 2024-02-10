package com.api.ttoklip.domain.town.community.like.repository;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.town.community.like.entity.CommunityLike;
import com.api.ttoklip.domain.town.community.post.entity.Community;

public interface LikeCustomRepositoty {

    CommunityLike findByMemberAndCommunity(Member member, Community community);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);

}
